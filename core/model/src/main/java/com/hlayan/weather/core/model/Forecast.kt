package com.hlayan.weather.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val forecastDay: List<ForecastDay>
)