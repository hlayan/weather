package com.hlayan.weather.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDay(
    val date: String,
    val dateEpoch: Int,
    val day: Day,
    val astro: Astro,
    val hour: List<Hour>
)