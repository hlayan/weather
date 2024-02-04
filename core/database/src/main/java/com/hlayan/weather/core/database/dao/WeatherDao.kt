package com.hlayan.weather.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hlayan.weather.core.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherEntity: WeatherEntity)

    @Query("DELETE FROM weather")
    suspend fun clearWeathers()

    @Query("DELETE FROM weather WHERE locationName = :locationName")
    suspend fun deleteWeather(locationName: String)

    @Query("SELECT * FROM weather WHERE locationName = :locationName")
    fun getWeather(locationName: String): Flow<WeatherEntity?>

}