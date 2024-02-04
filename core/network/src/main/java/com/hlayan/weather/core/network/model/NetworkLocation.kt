package com.hlayan.weather.core.network.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NetworkSearchedLocation(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("region") val region: String,
    @SerialName("country") val country: String,
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("url") val url: String
)