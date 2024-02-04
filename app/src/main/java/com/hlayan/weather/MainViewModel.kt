package com.hlayan.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlayan.weather.core.data.repository.UserDataRepository
import com.hlayan.weather.core.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: UserDataRepository,
) : ViewModel() {

    val userData: StateFlow<UserPreferences> = repository.userData.stateIn(
        scope = viewModelScope,
        initialValue = UserPreferences(darkTheme = false),
        started = SharingStarted.WhileSubscribed(5_000)
    )
}
