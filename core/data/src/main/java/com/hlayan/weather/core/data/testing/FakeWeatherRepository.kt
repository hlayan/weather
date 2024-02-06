package com.hlayan.weather.core.data.testing

import JvmUnitTestFakeAssetManager
import androidx.annotation.VisibleForTesting
import com.hlayan.weather.core.data.model.asEntity
import com.hlayan.weather.core.data.model.asWeather
import com.hlayan.weather.core.model.Weather
import com.hlayan.weather.core.network.model.NetworkWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@VisibleForTesting
class FakeWeatherRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: JvmUnitTestFakeAssetManager = JvmUnitTestFakeAssetManager,
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getWeather(): Weather {
        return withContext(ioDispatcher) {
            val networkWeather: NetworkWeather =
                assets.open(WEATHER_ASSET).use(networkJson::decodeFromStream)
            networkWeather.asEntity().asWeather()
        }
    }

    companion object {
        private const val WEATHER_ASSET = "weather.json"
    }
}