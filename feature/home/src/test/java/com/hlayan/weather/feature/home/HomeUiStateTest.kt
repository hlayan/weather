package com.hlayan.weather.feature.home

import JvmUnitTestFakeAssetManager
import com.hlayan.weather.core.data.testing.FakeWeatherRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HomeUiStateTest {

    private lateinit var subject: FakeWeatherRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        subject = FakeWeatherRepository(
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Test
    fun asHomeUiState_shouldFormat_properly() = runTest(testDispatcher) {
        val homeUiState = subject.getWeather().asHomeUiState()

        Assert.assertEquals("London", homeUiState.location)
        Assert.assertEquals("13°", homeUiState.currentTempC)
        Assert.assertEquals("11°", homeUiState.feelLikeTempC)
        Assert.assertEquals("Partly cloudy", homeUiState.weatherCondition)
        Assert.assertEquals(
            "https://cdn.weatherapi.com/weather/64x64/day/116.png",
            homeUiState.weatherIcon
        )
        Assert.assertEquals("Fri, 2 Feb", homeUiState.weatherDateTime)
        Assert.assertEquals("3.0", homeUiState.uvIndex)
        Assert.assertEquals("77%", homeUiState.humidity)
        Assert.assertEquals("24.1 km/h", homeUiState.wind)
        Assert.assertEquals("1029.0 mb", homeUiState.pressure)
        Assert.assertEquals("10.0 km", homeUiState.visibility)

        val expectedForecastDayUiState = ForecastDayUiState(
            tempC = "13°/10°",
            date = "Sat, 3 Feb",
            icon = "https://cdn.weatherapi.com/weather/64x64/day/122.png",
            condition = "Overcast "
        )
        Assert.assertEquals(expectedForecastDayUiState, homeUiState.forecastDays[0])

        val expectedForecastHourUiState = ForecastHourUiState(
            tempC = "6°",
            time = "12 AM",
            icon = "https://cdn.weatherapi.com/weather/64x64/night/113.png"
        )
        Assert.assertEquals(expectedForecastHourUiState, homeUiState.forecastHours[0])

    }

}