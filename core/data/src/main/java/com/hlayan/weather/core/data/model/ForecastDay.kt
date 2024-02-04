package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.model.ForecastDay
import com.hlayan.weather.core.network.model.NetworkForecastDay

fun NetworkForecastDay.asForecastDay() = ForecastDay(
    date = date,
    dateEpoch = dateEpoch,
    day = day.asDay(),
    astro = astro.asAstro(),
    hour = hour.map { it.asHour() }
)