package com.hlayan.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hlayan.weather.feature.home.navigation.HOME_ROUTE
import com.hlayan.weather.feature.home.navigation.homeScreen
import com.hlayan.weather.feature.home.navigation.navigateToHomeScreen
import com.hlayan.weather.feature.location.navigation.LOCATION_ROUTE
import com.hlayan.weather.feature.location.navigation.locationScreen
import com.hlayan.weather.feature.location.navigation.navigateToLocationScreen

@Composable
fun WeatherNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = LOCATION_ROUTE,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        locationScreen(
            onBackClick = { navController.popBackStack(HOME_ROUTE, false) },
            onLocationItemClick = { location, previousLocation ->
                if (location == previousLocation) navController.popBackStack()
                else {
                    navController.navigateToHomeScreen(location) {
                        popUpTo(if (previousLocation == null) LOCATION_ROUTE else HOME_ROUTE) {
                            inclusive = true
                        }
                    }
                }
            }
        )

        homeScreen(onSearchClick = navController::navigateToLocationScreen)
    }
}
