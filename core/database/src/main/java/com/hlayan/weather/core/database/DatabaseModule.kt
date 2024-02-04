package com.hlayan.weather.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun providesWeatherDatabase(
        @ApplicationContext context: Context,
    ): WeatherDatabase = Room.databaseBuilder(
        context, WeatherDatabase::class.java, "weather_database"
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .enableMultiInstanceInvalidation()
        .build()

}