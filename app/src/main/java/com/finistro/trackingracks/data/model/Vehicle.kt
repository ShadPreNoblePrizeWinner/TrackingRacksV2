package com.finistro.trackingracks.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    val make: String = "",
    val model: String = "",
    val year: String = "",
    val mileage: String = "",
    val mpg: String = ""
)
