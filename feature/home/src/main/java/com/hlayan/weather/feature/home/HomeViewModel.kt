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
import com.hlayan.weather.core.ui.util.asLocalDate
import com.hlayan.weather.core.ui.util.asLocalDateTime
import com.hlayan.weather.core.ui.util.format
import com.hlayan.weather.feature.home.navigation.HomeScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: WeatherRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    private val args: HomeScreenArgs = HomeScreenArgs(savedStateHandle)

    val locationName = args.locationName

    val uiState = repository.getWeather(args.locationName)
        .map { weather ->
            if (weather == null) HomeUiState()
            else HomeUiState(
                location = weather.location.run { "$name, $country" },
                currentTempC = "${weather.current.tempC.roundToInt()}°",
                feelLikeTempC = "${weather.current.feelslikeC.roundToInt()}°",
                weatherCondition = weather.current.condition.text,
                weatherDateTime = weather.location.localtime,
                uvIndex = "${weather.current.uv}",
                humidity = "${weather.current.humidity}%",
                wind = "${weather.current.windKph} km/h",
                pressure = "${weather.current.pressureMb} mb",
                visibility = "${weather.current.visKm} km",
                forecastDays = weather.forecast.forecastDay.map {
                    ForecastDayUiState(
                        tempC = "${it.day.maxtempC.roundToInt()}°/${it.day.mintempC.roundToInt()}°",
                        date = it.date.asLocalDate().format("dd MMM")
                    )
                }.toImmutableList(),
                forecastHours = weather.forecast.forecastDay[0].hour.map {
                    ForecastHourUiState(
                        tempC = "${it.tempC.roundToInt()}°",
                        time = it.time.asLocalDateTime().format("h a")
                    )
                }.toImmutableList()
            )
        }
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

    private val _isLoading = MutableStateFlow(false)
    val loading = _isLoading.asStateFlow()

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
                _isLoading.value = true
                repository.refreshWeather(args.locationName, 6)
            } catch (e: Exception) {
                _messageEvent.sendEvent("Cannot refresh!")
            } finally {
                _isLoading.value = false
            }
        }
    }

}