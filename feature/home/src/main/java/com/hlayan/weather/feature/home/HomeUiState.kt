package com.hlayan.weather.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class HomeUiState(
    val location: String = "-",
    val currentTempC: String = "-",
    val feelLikeTempC: String = "-",
    val weatherCondition: String = "-",
    val weatherDateTime: String = "-",
    val uvIndex: String = "-",
    val humidity: String = "-",
    val wind: String = "-",
    val pressure: String = "-",
    val visibility: String = "-",
    val forecastDays: ImmutableList<ForecastDayUiState> = emptyList<ForecastDayUiState>().toImmutableList(),
    val forecastHours: ImmutableList<ForecastHourUiState> = emptyList<ForecastHourUiState>().toImmutableList(),
)

data class ForecastDayUiState(
    val tempC: String,
    val date: String,
    val icon: ImageVector = Icons.Default.WbSunny
)

data class ForecastHourUiState(
    val tempC: String,
    val time: String,
    val icon: ImageVector = Icons.Default.WbSunny
)