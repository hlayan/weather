package com.hlayan.weather.feature.home.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hlayan.weather.feature.home.HomeScreen

internal const val LOCATION_NAME = "location_name"
internal const val BASE_HOME_ROUTE = "home_route"
const val HOME_ROUTE = "$BASE_HOME_ROUTE/{$LOCATION_NAME}"

internal class HomeScreenArgs(val locationName: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle.get<String>(LOCATION_NAME))
    )
}

fun NavController.navigateToHomeScreen(
    locationName: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("$BASE_HOME_ROUTE/$locationName", builder)
}

fun NavGraphBuilder.homeScreen(
    onSearchClick: (String) -> Unit,
) {
    composable(
        route = HOME_ROUTE,
        arguments = listOf(
            navArgument(LOCATION_NAME) { type = NavType.StringType },
        ),
    ) {
        HomeScreen(onSearchClick = onSearchClick)
    }
}
