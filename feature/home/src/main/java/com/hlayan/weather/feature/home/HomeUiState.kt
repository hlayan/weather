package com.hlayan.weather.feature.home

import com.hlayan.weather.core.model.Weather
import com.hlayan.weather.core.ui.util.asLocalDate
import com.hlayan.weather.core.ui.util.asLocalDateTime
import com.hlayan.weather.core.ui.util.format
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.roundToInt

data class HomeUiState(
    val location: String = "-",
    val currentTempC: String = "-",
    val feelLikeTempC: String = "-",
    val weatherCondition: String = "-",
    val weatherIcon: String = "",
    val weatherDateTime: String = "-",
    val uvIndex: String = "-",
    val humidity: String = "-",
    val wind: String = "-",
    val pressure: String = "-",
    val visibility: String = "-",
    val forecastDays: ImmutableList<ForecastDayUiState> = emptyList<ForecastDayUiState>().toImmutableList(),
    val forecastHours: ImmutableList<ForecastHourUiState> = emptyList<ForecastHourUiState>().toImmutableList(),
)

internal fun Weather.asHomeUiState() = HomeUiState(
    location = location.name,
    currentTempC = "${current.tempC.roundToInt()}°",
    feelLikeTempC = "${current.feelslikeC.roundToInt()}°",
    weatherCondition = current.condition.text,
    weatherIcon = "https:" + current.condition.icon,
    weatherDateTime = location.localtime.asLocalDateTime().format("E, d MMM"),
    uvIndex = "${current.uv}",
    humidity = "${current.humidity}%",
    wind = "${current.windKph} km/h",
    pressure = "${current.pressureMb} mb",
    visibility = "${current.visKm} km",
    forecastDays = forecast.forecastDay.subList(1, forecast.forecastDay.size).map {
        ForecastDayUiState(
            tempC = "${it.day.maxtempC.roundToInt()}°/${it.day.mintempC.roundToInt()}°",
            date = it.date.asLocalDate().format("E, d MMM"),
            icon = "https:" + it.day.condition.icon,
            condition = it.day.condition.text
        )
    }.toImmutableList(),
    forecastHours = forecast.forecastDay[0].hour.map {
        ForecastHourUiState(
            tempC = "${it.tempC.roundToInt()}°",
            time = it.time.asLocalDateTime().format("h a"),
            icon = "https:" + it.condition.icon
        )
    }.toImmutableList()
)

data class ForecastDayUiState(
    val tempC: String,
    val date: String,
    val icon: String,
    val condition: String
)

data class ForecastHourUiState(
    val tempC: String,
    val time: String,
    val icon: String
)