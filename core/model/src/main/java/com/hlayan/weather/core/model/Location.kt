package com.hlayan.weather.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tzId: String,
    val localtimeEpoch: Int,
    val localtime: String
)