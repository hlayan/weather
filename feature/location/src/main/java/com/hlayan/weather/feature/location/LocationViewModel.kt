package com.hlayan.weather.feature.location

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hlayan.weather.core.data.repository.WeatherRepository
import com.hlayan.weather.core.model.SearchedLocation
import com.hlayan.weather.feature.location.navigation.LocationScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: WeatherRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args: LocationScreenArgs = LocationScreenArgs(savedStateHandle)

    val previousLocation = args.previousLocation

    private val _isLoading = MutableStateFlow(false)
    val loading = _isLoading.asStateFlow()

    val savedLocations = repository.savedLocations.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    private val _searchedLocations = MutableStateFlow(emptyList<SearchedLocation>())
    val searchedLocations = _searchedLocations.asStateFlow()

    private val _input = MutableStateFlow("")
    val input = _input.asStateFlow()

    fun onInputChange(value: String) {
        _input.value = value
        if (value.isBlank()) _searchedLocations.value = emptyList()

    }

    fun onRemoveLocation(id: Int) {
        viewModelScope.launch {
            repository.removeLocation(id)
        }
    }

    fun onSaveLocation(searchedLocation: SearchedLocation) {
        viewModelScope.launch {
            repository.saveLocation(searchedLocation)
        }
    }

    fun searchLocations() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val results = repository.searchLocations(_input.value)
                _searchedLocations.update { results }
            } catch (e: Exception) {
                _searchedLocations.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

}