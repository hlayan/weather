package com.hlayan.weather.core.datastore

import androidx.datastore.core.DataStore
import com.hlayan.weather.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val userData: Flow<UserPreferences> = userPreferences.data

    suspend fun setTheme(darkTheme: Boolean) {
        userPreferences.updateData {
            it.copy(darkTheme = darkTheme)
        }
    }

    suspend fun toggleDarkMode() {
        userPreferences.updateData {
            it.copy(darkTheme = !it.darkTheme)
        }
    }
}