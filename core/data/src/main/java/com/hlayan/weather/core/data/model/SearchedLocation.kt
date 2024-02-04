package com.hlayan.weather.core.data.model

import com.hlayan.weather.core.database.entity.SavedLocationEntity
import com.hlayan.weather.core.model.SearchedLocation
import com.hlayan.weather.core.network.model.NetworkSearchedLocation

fun SearchedLocation.asEntity() = SavedLocationEntity(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    url = url
)

fun NetworkSearchedLocation.asSearchedLocation() = SearchedLocation(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    url = url
)

fun SavedLocationEntity.asSearchedLocation() = SearchedLocation(
    id = id,
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon,
    url = url
)