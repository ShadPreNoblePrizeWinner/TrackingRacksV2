package com.finistro.trackingracks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.finistro.trackingracks.data.model.DailyExpense
import com.finistro.trackingracks.data.model.FixedExpense
import com.finistro.trackingracks.data.model.GigEntry
import com.finistro.trackingracks.data.model.Vehicle
import com.finistro.trackingracks.repository.GigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale

class GigViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GigRepository(application)

    private val _gigs = MutableStateFlow<List<GigEntry>>(emptyList())
    val gigs = _gigs.asStateFlow()

    private val _fixedExpenses = MutableStateFlow<List<FixedExpense>>(emptyList())
    val fixedExpenses = _fixedExpenses.asStateFlow()

    private val _dailyExpenses = MutableStateFlow<List<DailyExpense>>(emptyList())
    val dailyExpenses = _dailyExpenses.asStateFlow()

    private val _vehicle = MutableStateFlow(Vehicle())
    val vehicle = _vehicle.asStateFlow()

    init {
        load()
        if (_gigs.value.isEmpty() && _fixedExpenses.value.isEmpty() && _dailyExpenses.value.isEmpty()) {
            populateTestData()
        }
    }

    private fun populateTestData() {
        viewModelScope.launch {
            val apps = listOf("UberEats", "Doordash", "Instacart", "Grubhub", "Lyft", "Spark", "Amazon Flex")
            val cities = listOf("Springfield", "Shelbyville", "Capital City", "Ogdenville", "North Haverbrook", "Brockway")
            val randomGigs = (1..100).map { i ->
                val isAccepted = (0..10).random() > 2
                val isCompleted = if (isAccepted) (0..10).random() > 1 else false
                val isDeclined = !isAccepted
                val date = LocalDate.now().minusDays((0..60).random().toLong())
                
                GigEntry(
                    id = i,
                    title = "Gig #$i",
                    offerAmount = (5..65).random().toDouble() + ((0..99).random() / 100.0),
                    distanceMiles = (1..25).random().toDouble() + ((0..9).random() / 10.0),
                    city = cities.random(),
                    date = if ((0..20).random() > 1) date.toString() else "", // Very few with no date
                    appNameUsed = apps.random(),
                    dayOfWeek = date.dayOfWeek.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    acceptedCount = if (isAccepted) 1 else 0,
                    declinedCount = if (isDeclined) 1 else 0,
                    completedCount = if (isCompleted) 1 else 0,
                    timeTaken = if (isCompleted) (15..120).random() else 0
                )
            }
            randomGigs.forEach { repo.addGig(it) }

            val testFixedExpenses = listOf(
                FixedExpense(name = "Insurance", amount = 145.0),
                FixedExpense(name = "Lease/Loan", amount = 380.0),
                FixedExpense(name = "Subscriptions", amount = 25.0),
                FixedExpense(name = "Phone Bill", amount = 85.0)
            )
            testFixedExpenses.forEach { repo.addFixedExpense(it) }

            val testDailyExpenses = (1..15).map { i ->
                val date = LocalDate.now().minusDays((0..30).random().toLong())
                val categories = listOf("Gas", "Food", "Maintenance", "Parking")
                val category = categories.random()
                DailyExpense(
                    name = "$category #$i",
                    amount = (5..50).random().toDouble() + ((0..99).random() / 100.0),
                    category = category,
                    date = date.toString()
                )
            }
            testDailyExpenses.forEach { repo.addDailyExpense(it) }
            
            load()
        }
    }

    private fun load() {
        _gigs.value = repo.loadGigs()
        _fixedExpenses.value = repo.loadFixedExpenses()
        _dailyExpenses.value = repo.loadDailyExpenses()
        _vehicle.value = repo.loadVehicle()
    }

    fun addGig(entry: GigEntry) {
        viewModelScope.launch {
            repo.addGig(entry)
            load()
        }
    }

    fun addFixedExpense(expense: FixedExpense) {
        viewModelScope.launch {
            repo.addFixedExpense(expense)
            load()
        }
    }

    fun deleteFixedExpense(id: String) {
        viewModelScope.launch {
            repo.deleteFixedExpense(id)
            load()
        }
    }

    fun addDailyExpense(expense: DailyExpense) {
        viewModelScope.launch {
            repo.addDailyExpense(expense)
            load()
        }
    }

    fun deleteDailyExpense(id: String) {
        viewModelScope.launch {
            repo.deleteDailyExpense(id)
            load()
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            repo.saveVehicle(vehicle)
            load()
        }
    }
}
