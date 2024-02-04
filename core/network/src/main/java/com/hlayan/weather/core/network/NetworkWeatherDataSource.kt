package com.hlayan.weather.core.network

import com.hlayan.weather.core.network.model.NetworkSearchedLocation
import com.hlayan.weather.core.network.model.NetworkWeather
import javax.inject.Inject

class NetworkWeatherDataSource @Inject constructor(
    private val service: WeatherService,
) : WeatherDataSource {

    override suspend fun getWeather(name: String, days: Int): NetworkWeather {
        return service.getWeather(name, days)
    }

    override suspend fun searchLocations(name: String): List<NetworkSearchedLocation> {
        return service.searchLocation(name)
    }

}