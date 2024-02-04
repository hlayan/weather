package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.model.Hour
import com.hlayan.weather.core.network.model.NetworkHour

fun NetworkHour.asHour() = Hour(
    timeEpoch = timeEpoch,
    time = time,
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
    snowCm = snowCm,
    humidity = humidity,
    cloud = cloud,
    feelslikeC = feelslikeC,
    feelslikeF = feelslikeF,
    windchillC = windchillC,
    windchillF = windchillF,
    heatindexC = heatindexC,
    heatindexF = heatindexF,
    dewpointC = dewpointC,
    dewpointF = dewpointF,
    willItRain = willItRain,
    chanceOfRain = chanceOfRain,
    willItSnow = willItSnow,
    chanceOfSnow = chanceOfSnow,
    visKm = visKm,
    visMiles = visMiles,
    gustMph = gustMph,
    gustKph = gustKph,
    uv = uv,
    shortRad = shortRad,
    diffRad = diffRad
)