package com.hlayan.weather.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.hlayan.weather.core.datastore.UserPreferencesSerializer
import com.hlayan.weather.core.model.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context,
        serializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> = DataStoreFactory.create(serializer = serializer) {
        context.dataStoreFile("user_preferences.pb")
    }
}
