package com.hlayan.weather.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_location")
data class SavedLocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String
)
