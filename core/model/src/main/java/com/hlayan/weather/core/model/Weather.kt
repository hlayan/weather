package com.hlayan.weather.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val locationName: String,
    val location: Location,
    val current: Current,
    val forecast: Forecast
)