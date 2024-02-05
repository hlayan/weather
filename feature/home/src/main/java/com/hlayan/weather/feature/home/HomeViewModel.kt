package com.hlayan.weather.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlayan.weather.core.data.repository.UserDataRepository
import com.hlayan.weather.core.data.repository.WeatherRepository
import com.hlayan.weather.core.model.UserPreferences
import com.hlayan.weather.core.ui.common.asFlowEvent
import com.hlayan.weather.core.ui.common.mutableFlowEvent
import com.hlayan.weather.core.ui.common.sendEvent
import com.hlayan.weather.feature.home.navigation.HomeScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: WeatherRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    private val args: HomeScreenArgs = HomeScreenArgs(savedStateHandle)

    val locationName = args.locationName

    val uiState: StateFlow<HomeUiState> = repository.getWeather(args.locationName)
        .map { it?.asHomeUiState() ?: HomeUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = HomeUiState()
        )

    val userData: StateFlow<UserPreferences> = userDataRepository.userData.stateIn(
        scope = viewModelScope,
        initialValue = UserPreferences(darkTheme = false),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _messageEvent = mutableFlowEvent<String>()
    val messageEvent = _messageEvent.asFlowEvent()

    init {
        refreshWeather()
    }

    fun toggleDarkTheme() {
        viewModelScope.launch {
            userDataRepository.toggleDarkMode()
        }
    }

    fun refreshWeather() {
        viewModelScope.launch {
            try {
                _refreshing.value = true
                repository.refreshWeather(args.locationName, 6)
            } catch (e: Exception) {
                _messageEvent.sendEvent("Cannot refresh!")
            } finally {
                _refreshing.value = false
            }
        }
    }

}