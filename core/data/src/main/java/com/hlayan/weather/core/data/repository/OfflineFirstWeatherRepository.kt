package com.hlayan.weather.core.data.repository

import com.hlayan.weather.core.data.model.asEntity
import com.hlayan.weather.core.data.model.asSearchedLocation
import com.hlayan.weather.core.data.model.asWeather
import com.hlayan.weather.core.database.dao.SavedLocationDao
import com.hlayan.weather.core.database.dao.WeatherDao
import com.hlayan.weather.core.model.SearchedLocation
import com.hlayan.weather.core.model.Weather
import com.hlayan.weather.core.network.WeatherDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstWeatherRepository @Inject constructor(
    private val network: WeatherDataSource,
    private val weatherDao: WeatherDao,
    private val savedLocationDao: SavedLocationDao,
) : WeatherRepository {

    override val savedLocations: Flow<List<SearchedLocation>> =
        savedLocationDao.getLocations().map { items -> items.map { it.asSearchedLocation() } }

    override fun getWeather(locationName: String): Flow<Weather?> {
        return weatherDao.getWeather(locationName).map { it?.asWeather() }
    }

    override suspend fun refreshWeather(name: String, days: Int) {
        weatherDao.insert(network.getWeather(name, days).asEntity())
    }

    override suspend fun searchLocations(name: String): List<SearchedLocation> {
        return network.searchLocations(name).map { it.asSearchedLocation() }
    }

    override suspend fun removeLocation(id: Int) {
        savedLocationDao.removeLocation(id)
    }

    override suspend fun saveLocation(searchedLocation: SearchedLocation) {
        savedLocationDao.insert(searchedLocation.asEntity())
    }

}