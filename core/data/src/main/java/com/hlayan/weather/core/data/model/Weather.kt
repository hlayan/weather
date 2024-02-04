package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.database.entity.WeatherEntity
import com.hlayan.weather.core.model.Weather
import com.hlayan.weather.core.network.model.NetworkWeather

fun NetworkWeather.asEntity() = WeatherEntity(
    locationName = location.name,
    location = location.asLocation(),
    current = current.asCurrent(),
    forecast = forecast.asForecast()
)

fun WeatherEntity.asWeather() = Weather(
    locationName = locationName,
    location = location,
    current = current,
    forecast = forecast
)