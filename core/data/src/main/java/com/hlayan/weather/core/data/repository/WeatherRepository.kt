package com.hlayan.weather.core.data.repository

import com.hlayan.weather.core.model.SearchedLocation
import com.hlayan.weather.core.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    val savedLocations: Flow<List<SearchedLocation>>

    fun getWeather(locationName: String): Flow<Weather?>

    suspend fun refreshWeather(name: String, days: Int)

    suspend fun searchLocations(name: String): List<SearchedLocation>

    suspend fun removeLocation(id: Int)

    suspend fun saveLocation(searchedLocation: SearchedLocation)

}