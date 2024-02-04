package com.hlayan.weather.core.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindWeatherRepository(repository: OfflineFirstWeatherRepository): WeatherRepository

    @Singleton
    @Binds
    fun bindUserDataRepository(repository: DefaultUserDataRepository): UserDataRepository
}