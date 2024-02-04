package com.hlayan.weather.core.database

import com.hlayan.weather.core.database.dao.SavedLocationDao
import com.hlayan.weather.core.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun provideWeatherDao(
        database: WeatherDatabase,
    ): WeatherDao = database.getWeatherDao()

    @Provides
    fun provideSavedLocationDao(
        database: WeatherDatabase,
    ): SavedLocationDao = database.getSavedLocationDao()
}
