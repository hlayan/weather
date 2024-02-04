package com.hlayan.weather.core.database.converter

import androidx.room.TypeConverter
import com.hlayan.weather.core.model.Current
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class CurrentConverter {

    @TypeConverter
    fun stringToCurrent(value: String): Current? {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun currentToString(value: Current): String {
        return Json.encodeToString(value)
    }
}
