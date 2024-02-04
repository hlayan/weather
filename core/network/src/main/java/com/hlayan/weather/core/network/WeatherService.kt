package com.hlayan.weather.core.network

import com.hlayan.weather.core.network.model.NetworkSearchedLocation
import com.hlayan.weather.core.network.model.NetworkWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast.json")
    suspend fun getWeather(
        @Query("q") name: String,
        @Query("days") days: Int
    ): NetworkWeather

    @GET("search.json")
    suspend fun searchLocation(
        @Query("q") name: String
    ): List<NetworkSearchedLocation>

}