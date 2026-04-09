package com.finistro.trackingracks.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FixedExpense(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val amount: Double,
    val frequency: String = "Monthly" // "Monthly", "6 Months", etc.
)

@Serializable
data class DailyExpense(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val amount: Double,
    val date: String,
    val category: String,
    val establishment: String = ""
)
