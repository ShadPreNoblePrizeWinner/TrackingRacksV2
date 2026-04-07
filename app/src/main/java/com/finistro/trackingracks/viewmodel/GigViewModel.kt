package com.finistro.trackingracks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.finistro.trackingracks.data.model.DailyExpense
import com.finistro.trackingracks.data.model.FixedExpense
import com.finistro.trackingracks.data.model.GigEntry
import com.finistro.trackingracks.repository.GigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GigViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GigRepository(application)

    private val _gigs = MutableStateFlow<List<GigEntry>>(emptyList())
    val gigs = _gigs.asStateFlow()

    private val _fixedExpenses = MutableStateFlow<List<FixedExpense>>(emptyList())
    val fixedExpenses = _fixedExpenses.asStateFlow()

    private val _dailyExpenses = MutableStateFlow<List<DailyExpense>>(emptyList())
    val dailyExpenses = _dailyExpenses.asStateFlow()

    init {
        load()
    }

    private fun load() {
        _gigs.value = repo.loadGigs()
        _fixedExpenses.value = repo.loadFixedExpenses()
        _dailyExpenses.value = repo.loadDailyExpenses()
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
}
