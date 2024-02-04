package com.hlayan.weather.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hlayan.weather.core.model.Current
import com.hlayan.weather.core.model.Forecast
import com.hlayan.weather.core.model.Location

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val locationName: String,
    val location: Location,
    val current: Current,
    val forecast: Forecast
)
