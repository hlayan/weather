package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.model.Forecast
import com.hlayan.weather.core.network.model.NetworkForecast

fun NetworkForecast.asForecast() = Forecast(
    forecastDay = forecastDay.map { it.asForecastDay() }
)