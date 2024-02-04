package com.hlayan.weather.core.database.converter

import androidx.room.TypeConverter
import com.hlayan.weather.core.model.Forecast
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ForecastConverter {

    @TypeConverter
    fun stringToForecast(value: String): Forecast? {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun forecastToString(value: Forecast): String {
        return Json.encodeToString(value)
    }
}
