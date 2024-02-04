package com.hlayan.weather.core.data.repository

import com.hlayan.weather.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserPreferences>

    suspend fun setTheme(darkTheme: Boolean)

    suspend fun toggleDarkMode()

}