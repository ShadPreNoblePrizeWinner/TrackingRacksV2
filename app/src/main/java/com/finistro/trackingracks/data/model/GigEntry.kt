package com.finistro.trackingracks.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GigEntry(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val offerAmount: Double = 0.0,
    val distanceMiles: Double = 0.0,
    val city: String = "",
    val state: String = "",
    val isDoubleOrder: Boolean = false,
    val isAddOn: Boolean = false,
    val isLowball: Double = 0.0,
    val gasPrice: Double = 0.0,
    val weather: String = "",
    val weatherTemp: Double = 0.0,
    val dayOfWeek: String = "",
    val timeOfOffer: String = "",
    val timeOfOrder: String = "",
    val isAHoliday: Boolean = false,
    val isWeekend: Boolean = false,
    val appNameUsed: String = "",
    val appVersion: String = "",
    val appCrash: Boolean = false,
    val crashDetails: String = "",
    val acceptedCount: Int = 1,
    val declinedCount: Int = 0,
    val completedCount: Int = 1,
    val timeTaken: Int = 0
)