package com.hlayan.weather.core.network.di

import com.hlayan.weather.core.network.NetworkWeatherDataSource
import com.hlayan.weather.core.network.WeatherDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Singleton
    @Binds
    fun bindNetworkWeatherDataSource(dataSource: NetworkWeatherDataSource): WeatherDataSource
}