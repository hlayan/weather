package com.hlayan.weather.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hlayan.weather.core.database.entity.SavedLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SavedLocationEntity)

    @Query("DELETE FROM saved_location WHERE id = :id")
    suspend fun removeLocation(id: Int)

    @Query("SELECT * FROM saved_location")
    fun getLocations(): Flow<List<SavedLocationEntity>>

}