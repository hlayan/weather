package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.model.Day
import com.hlayan.weather.core.network.model.NetworkDay

fun NetworkDay.asDay() = Day(
    maxtempC = maxtempC,
    maxtempF = maxtempF,
    mintempC = mintempC,
    mintempF = mintempF,
    avgtempC = avgtempC,
    avgtempF = avgtempF,
    maxwindMph = maxwindMph,
    maxwindKph = maxwindKph,
    totalprecipMm = totalprecipMm,
    totalprecipIn = totalprecipIn,
    totalsnowCm = totalsnowCm,
    avgvisKm = avgvisKm,
    avgvisMiles = avgvisMiles,
    avghumidity = avghumidity,
    dailyWillItRain = dailyWillItRain,
    dailyChanceOfRain = dailyChanceOfRain,
    dailyWillItSnow = dailyWillItSnow,
    dailyChanceOfSnow = dailyChanceOfSnow,
    condition = condition.asCondition(),
    uv = uv
)