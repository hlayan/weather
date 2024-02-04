package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.model.Location
import com.hlayan.weather.core.network.model.NetworkLocation

fun NetworkLocation.asLocation() = Location(
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    tzId = tzId,
    localtimeEpoch = localtimeEpoch,
    localtime = localtime
)