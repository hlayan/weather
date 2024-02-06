package com.hlayan.weather.core.network.fake

import android.content.Context
import com.hlayan.weather.core.network.WeatherDataSource
import com.hlayan.weather.core.network.model.NetworkSearchedLocation
import com.hlayan.weather.core.network.model.NetworkWeather
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class FakeWeatherDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val networkJson: Json,
) : WeatherDataSource {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getWeather(name: String, days: Int): NetworkWeather {
        return withContext(Dispatchers.IO) {
            context.assets.open(WEATHER_ASSET).use(networkJson::decodeFromStream)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun searchLocations(name: String): List<NetworkSearchedLocation> {
        return withContext(Dispatchers.IO) {
            context.assets.open(SEARCH_ASSET).use(networkJson::decodeFromStream)
        }
    }

    companion object {
        private const val WEATHER_ASSET = "weather.json"
        private const val SEARCH_ASSET = "search.json"
    }
}
