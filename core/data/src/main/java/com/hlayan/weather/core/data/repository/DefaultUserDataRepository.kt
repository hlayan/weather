package com.hlayan.weather.core.data.repository

import com.hlayan.weather.core.datastore.UserPreferencesDataSource
import com.hlayan.weather.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultUserDataRepository @Inject constructor(
    private val dataSource: UserPreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserPreferences> = dataSource.userData

    override suspend fun setTheme(darkTheme: Boolean) {
        dataSource.setTheme(darkTheme)
    }

    override suspend fun toggleDarkMode() {
        dataSource.toggleDarkMode()
    }

}