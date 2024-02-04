package com.hlayan.weather.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val darkTheme: Boolean
)