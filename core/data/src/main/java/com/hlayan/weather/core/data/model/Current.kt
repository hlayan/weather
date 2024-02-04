package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.model.Current
import com.hlayan.weather.core.network.model.NetworkCurrent

fun NetworkCurrent.asCurrent() = Current(
    lastUpdatedEpoch = lastUpdatedEpoch,
    lastUpdated = lastUpdated,
    tempC = tempC,
    tempF = tempF,
    isDay = isDay,
    condition = condition.asCondition(),
    windMph = windMph,
    windKph = windKph,
    windDegree = windDegree,
    windDir = windDir,
    pressureMb = pressureMb,
    pressureIn = pressureIn,
    precipMm = precipMm,
    precipIn = precipIn,
    humidity = humidity,
    cloud = cloud,
    feelslikeC = feelslikeC,
    feelslikeF = feelslikeF,
    visKm = visKm,
    visMiles = visMiles,
    uv = uv,
    gustMph = gustMph,
    gustKph = gustKph
)