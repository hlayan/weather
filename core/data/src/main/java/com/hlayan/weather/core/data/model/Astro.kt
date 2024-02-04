package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.model.Astro
import com.hlayan.weather.core.network.model.NetworkAstro

fun NetworkAstro.asAstro() = Astro(
    sunrise = sunrise,
    sunset = sunset,
    moonrise = moonrise,
    moonset = moonset,
    moonPhase = moonPhase,
    moonIllumination = moonIllumination,
    isMoonUp = isMoonUp,
    isSunUp = isSunUp
)