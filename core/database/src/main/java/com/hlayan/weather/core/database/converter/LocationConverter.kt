package com.hlayan.weather.core.database.converter

import androidx.room.TypeConverter
import com.hlayan.weather.core.model.Location
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class LocationConverter {

    @TypeConverter
    fun stringToLocation(value: String): Location? {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun locationToString(value: Location): String {
        return Json.encodeToString(value)
    }
}
