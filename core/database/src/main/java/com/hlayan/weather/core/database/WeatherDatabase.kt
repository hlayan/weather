package com.hlayan.weather.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hlayan.weather.core.database.converter.CurrentConverter
import com.hlayan.weather.core.database.converter.ForecastConverter
import com.hlayan.weather.core.database.converter.LocationConverter
import com.hlayan.weather.core.database.dao.SavedLocationDao
import com.hlayan.weather.core.database.dao.WeatherDao
import com.hlayan.weather.core.database.entity.SavedLocationEntity
import com.hlayan.weather.core.database.entity.WeatherEntity

@Database(
    version = 1,
    entities = [
        WeatherEntity::class,
        SavedLocationEntity::class,
    ]
)
@TypeConverters(
    value = [
        CurrentConverter::class,
        ForecastConverter::class,
        LocationConverter::class,
    ]
)
internal abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    abstract fun getSavedLocationDao(): SavedLocationDao
}