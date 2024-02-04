package com.hlayan.weather.core.network

import com.hlayan.weather.core.network.model.NetworkSearchedLocation
import com.hlayan.weather.core.network.model.NetworkWeather

interface WeatherDataSource {

    suspend fun getWeather(name: String, days: Int): NetworkWeather

    suspend fun searchLocations(name: String): List<NetworkSearchedLocation>

}