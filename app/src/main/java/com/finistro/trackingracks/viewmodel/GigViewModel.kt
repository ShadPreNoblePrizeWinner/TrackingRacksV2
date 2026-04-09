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

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles = _vehicles.asStateFlow()

    init {
        load()
        if (_gigs.value.isEmpty() && _fixedExpenses.value.isEmpty() && _dailyExpenses.value.isEmpty()) {
            populateTestData()
        }
    }

    private fun populateTestData() {
        viewModelScope.launch {
            // ... (rest of the code remains the same until vehicles)
            
            val testVehicles = listOf(
                Vehicle(nickname = "Primary Van", make = "Ford", model = "Transit", year = "2022", mileage = "45000", mpg = "18"),
                Vehicle(nickname = "Backup Car", make = "Toyota", model = "Corolla", year = "2018", mileage = "82000", mpg = "32")
            )
            repo.saveVehicles(testVehicles)
            
            load()
        }
    }

    private fun load() {
        _gigs.value = repo.loadGigs()
        _fixedExpenses.value = repo.loadFixedExpenses()
        _dailyExpenses.value = repo.loadDailyExpenses()
        _vehicles.value = repo.loadVehicles()
    }

    // ... (other methods)

    fun addGig(gig: GigEntry) {
        viewModelScope.launch {
            repo.addGig(gig)
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

    fun addVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            val list = _vehicles.value.toMutableList()
            list.add(vehicle)
            repo.saveVehicles(list)
            load()
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            val list = _vehicles.value.map { if (it.id == vehicle.id) vehicle else it }
            repo.saveVehicles(list)
            load()
        }
    }

    fun deleteVehicle(id: String) {
        viewModelScope.launch {
            val list = _vehicles.value.filter { it.id != id }
            repo.saveVehicles(list)
            load()
        }
    }
}
