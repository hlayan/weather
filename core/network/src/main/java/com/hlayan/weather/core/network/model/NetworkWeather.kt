package com.hlayan.weather.core.network.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NetworkWeather(
    @SerialName("location") val location: NetworkLocation,
    @SerialName("current") val current: NetworkCurrent,
    @SerialName("forecast") val forecast: NetworkForecast
)

@Keep
@Serializable
data class NetworkLocation(
    @SerialName("name") val name: String,
    @SerialName("region") val region: String,
    @SerialName("country") val country: String,
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("tz_id") val tzId: String,
    @SerialName("localtime_epoch") val localtimeEpoch: Int,
    @SerialName("localtime") val localtime: String
)

@Keep
@Serializable
data class NetworkCurrent(
    @SerialName("last_updated_epoch") val lastUpdatedEpoch: Int,
    @SerialName("last_updated") val lastUpdated: String,
    @SerialName("temp_c") val tempC: Double,
    @SerialName("temp_f") val tempF: Double,
    @SerialName("is_day") val isDay: Int,
    @SerialName("condition") val condition: NetworkCondition,
    @SerialName("wind_mph") val windMph: Double,
    @SerialName("wind_kph") val windKph: Double,
    @SerialName("wind_degree") val windDegree: Int,
    @SerialName("wind_dir") val windDir: String,
    @SerialName("pressure_mb") val pressureMb: Double,
    @SerialName("pressure_in") val pressureIn: Double,
    @SerialName("precip_mm") val precipMm: Double,
    @SerialName("precip_in") val precipIn: Double,
    @SerialName("humidity") val humidity: Int,
    @SerialName("cloud") val cloud: Int,
    @SerialName("feelslike_c") val feelslikeC: Double,
    @SerialName("feelslike_f") val feelslikeF: Double,
    @SerialName("vis_km") val visKm: Double,
    @SerialName("vis_miles") val visMiles: Double,
    @SerialName("uv") val uv: Double,
    @SerialName("gust_mph") val gustMph: Double,
    @SerialName("gust_kph") val gustKph: Double
)

@Keep
@Serializable
data class NetworkForecast(
    @SerialName("forecastday") val forecastDay: List<NetworkForecastDay>
)

@Keep
@Serializable
data class NetworkCondition(
    @SerialName("text") val text: String,
    @SerialName("icon") val icon: String,
    @SerialName("code") val code: Int
)

@Keep
@Serializable
data class NetworkForecastDay(
    @SerialName("date") val date: String,
    @SerialName("date_epoch") val dateEpoch: Int,
    @SerialName("day") val day: NetworkDay,
    @SerialName("astro") val astro: NetworkAstro,
    @SerialName("hour") val hour: List<NetworkHour>
)

@Keep
@Serializable
data class NetworkDay(
    @SerialName("maxtemp_c") val maxtempC: Double,
    @SerialName("maxtemp_f") val maxtempF: Double,
    @SerialName("mintemp_c") val mintempC: Double,
    @SerialName("mintemp_f") val mintempF: Double,
    @SerialName("avgtemp_c") val avgtempC: Double,
    @SerialName("avgtemp_f") val avgtempF: Double,
    @SerialName("maxwind_mph") val maxwindMph: Double,
    @SerialName("maxwind_kph") val maxwindKph: Double,
    @SerialName("totalprecip_mm") val totalprecipMm: Double,
    @SerialName("totalprecip_in") val totalprecipIn: Double,
    @SerialName("totalsnow_cm") val totalsnowCm: Double,
    @SerialName("avgvis_km") val avgvisKm: Double,
    @SerialName("avgvis_miles") val avgvisMiles: Double,
    @SerialName("avghumidity") val avghumidity: Int,
    @SerialName("daily_will_it_rain") val dailyWillItRain: Int,
    @SerialName("daily_chance_of_rain") val dailyChanceOfRain: Int,
    @SerialName("daily_will_it_snow") val dailyWillItSnow: Int,
    @SerialName("daily_chance_of_snow") val dailyChanceOfSnow: Int,
    @SerialName("condition") val condition: NetworkCondition,
    @SerialName("uv") val uv: Double
)

@Keep
@Serializable
data class NetworkAstro(
    @SerialName("sunrise") val sunrise: String,
    @SerialName("sunset") val sunset: String,
    @SerialName("moonrise") val moonrise: String,
    @SerialName("moonset") val moonset: String,
    @SerialName("moon_phase") val moonPhase: String,
    @SerialName("moon_illumination") val moonIllumination: Int,
    @SerialName("is_moon_up") val isMoonUp: Int,
    @SerialName("is_sun_up") val isSunUp: Int
)

@Keep
@Serializable
data class NetworkHour(
    @SerialName("time_epoch") val timeEpoch: Int,
    @SerialName("time") val time: String,
    @SerialName("temp_c") val tempC: Double,
    @SerialName("temp_f") val tempF: Double,
    @SerialName("is_day") val isDay: Int,
    @SerialName("condition") val condition: NetworkCondition,
    @SerialName("wind_mph") val windMph: Double,
    @SerialName("wind_kph") val windKph: Double,
    @SerialName("wind_degree") val windDegree: Int,
    @SerialName("wind_dir") val windDir: String,
    @SerialName("pressure_mb") val pressureMb: Double,
    @SerialName("pressure_in") val pressureIn: Double,
    @SerialName("precip_mm") val precipMm: Double,
    @SerialName("precip_in") val precipIn: Double,
    @SerialName("snow_cm") val snowCm: Double,
    @SerialName("humidity") val humidity: Int,
    @SerialName("cloud") val cloud: Int,
    @SerialName("feelslike_c") val feelslikeC: Double,
    @SerialName("feelslike_f") val feelslikeF: Double,
    @SerialName("windchill_c") val windchillC: Double,
    @SerialName("windchill_f") val windchillF: Double,
    @SerialName("heatindex_c") val heatindexC: Double,
    @SerialName("heatindex_f") val heatindexF: Double,
    @SerialName("dewpoint_c") val dewpointC: Double,
    @SerialName("dewpoint_f") val dewpointF: Double,
    @SerialName("will_it_rain") val willItRain: Int,
    @SerialName("chance_of_rain") val chanceOfRain: Int,
    @SerialName("will_it_snow") val willItSnow: Int,
    @SerialName("chance_of_snow") val chanceOfSnow: Int,
    @SerialName("vis_km") val visKm: Double,
    @SerialName("vis_miles") val visMiles: Double,
    @SerialName("gust_mph") val gustMph: Double,
    @SerialName("gust_kph") val gustKph: Double,
    @SerialName("uv") val uv: Double,
    @SerialName("short_rad") val shortRad: Double,
    @SerialName("diff_rad") val diffRad: Double
)