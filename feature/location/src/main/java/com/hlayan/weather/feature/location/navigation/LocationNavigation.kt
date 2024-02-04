package com.hlayan.weather.feature.location.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hlayan.weather.feature.location.LocationScreen

internal const val PREVIOUS_LOCATION = "previous_location"
internal const val LOCATION_BASE_ROUTE = "location_route"
const val LOCATION_ROUTE = "$LOCATION_BASE_ROUTE/{$PREVIOUS_LOCATION}"

internal class LocationScreenArgs(val previousLocation: String?) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        savedStateHandle.get<String>(PREVIOUS_LOCATION)
    )
}

fun NavController.navigateToLocationScreen(
    previousLocation: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("$LOCATION_BASE_ROUTE/$previousLocation", builder)
}

fun NavGraphBuilder.locationScreen(
    onBackClick: () -> Unit,
    onLocationItemClick: (location: String, previousLocation: String?) -> Unit,
) {
    composable(
        route = LOCATION_ROUTE,
        arguments = listOf(
            navArgument(PREVIOUS_LOCATION) { type = NavType.StringType },
        ),
    ) {
        LocationScreen(
            onBackClick = onBackClick,
            onLocationItemClick = onLocationItemClick,
        )
    }
}
