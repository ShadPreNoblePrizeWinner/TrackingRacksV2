package com.finistro.trackingracks.repository

import android.content.Context
import com.finistro.trackingracks.data.model.DailyExpense
import com.finistro.trackingracks.data.model.FixedExpense
import com.finistro.trackingracks.data.model.GigEntry
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class GigRepository(private val context: Context) {

    private val gigsFileName = "gigs.json"
    private val fixedExpensesFileName = "fixed_expenses.json"
    private val dailyExpensesFileName = "daily_expenses.json"
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    private fun gigsFile() = File(context.filesDir, gigsFileName)
    private fun fixedExpensesFile() = File(context.filesDir, fixedExpensesFileName)
    private fun dailyExpensesFile() = File(context.filesDir, dailyExpensesFileName)

    fun loadGigs(): List<GigEntry> {
        val f = gigsFile()
        if (!f.exists()) return emptyList()
        return json.decodeFromString(f.readText())
    }

    fun saveGigs(gigs: List<GigEntry>) {
        gigsFile().writeText(json.encodeToString(gigs))
    }

    fun addGig(gig: GigEntry) {
        val gigs = loadGigs().toMutableList()
        gigs.add(gig)
        saveGigs(gigs)
    }

    // Fixed Expenses
    fun loadFixedExpenses(): List<FixedExpense> {
        val f = fixedExpensesFile()
        if (!f.exists()) return emptyList()
        return json.decodeFromString(f.readText())
    }

    fun saveFixedExpenses(expenses: List<FixedExpense>) {
        fixedExpensesFile().writeText(json.encodeToString(expenses))
    }

    fun addFixedExpense(expense: FixedExpense) {
        val list = loadFixedExpenses().toMutableList()
        list.add(expense)
        saveFixedExpenses(list)
    }

    fun deleteFixedExpense(id: String) {
        val list = loadFixedExpenses().filter { it.id != id }
        saveFixedExpenses(list)
    }

    // Daily Expenses
    fun loadDailyExpenses(): List<DailyExpense> {
        val f = dailyExpensesFile()
        if (!f.exists()) return emptyList()
        return json.decodeFromString(f.readText())
    }

    fun saveDailyExpenses(expenses: List<DailyExpense>) {
        dailyExpensesFile().writeText(json.encodeToString(expenses))
    }

    fun addDailyExpense(expense: DailyExpense) {
        val list = loadDailyExpenses().toMutableList()
        list.add(expense)
        saveDailyExpenses(list)
    }

    fun deleteDailyExpense(id: String) {
        val list = loadDailyExpenses().filter { it.id != id }
        saveDailyExpenses(list)
    }
}
