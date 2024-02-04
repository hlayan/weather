package com.hlayan.weather.feature.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import com.hlayan.weather.core.ui.common.EventEffect
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeScreen(
    onSearchClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val toasterState = rememberToasterState()

    val userData by viewModel.userData.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val messageEvent by viewModel.messageEvent.collectAsStateWithLifecycle()

    EventEffect(event = messageEvent) {
        toasterState.show(it)
    }

    Scaffold { paddings ->
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HomeTopBar(
                uiState = uiState,
                darkTheme = userData.darkTheme,
                isRefreshing = loading,
                onRefresh = viewModel::refreshWeather,
                toggleDarkTheme = viewModel::toggleDarkTheme,
                onSearchClick = { onSearchClick(viewModel.locationName) },
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(top = paddings.calculateTopPadding())
            )

            Text(
                text = "Hourly forecast",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
            ForecastHourCard(
                items = uiState.forecastHours,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "Current conditions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(190.dp)
            ) {

                item {
                    ForecastItem(name = "Wind speed", value = uiState.wind)
                }

                item {
                    ForecastItem(name = "Humidity", value = uiState.humidity)
                }

                item {
                    ForecastItem(name = "Pressure", value = uiState.pressure)
                }

                item {
                    ForecastItem(name = "UV index", value = uiState.uvIndex)
                }
            }

            Text(
                text = "5-days forecast",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            ForecastDayCard(
                items = uiState.forecastDays,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.size(paddings.calculateBottomPadding() + 16.dp))

        }
    }

    Toaster(
        state = toasterState,
        darkTheme = isSystemInDarkTheme(),
        alignment = Alignment.TopCenter
    )

}

@Composable
private fun TabRow(items: List<String>, selectedDate: String, onItemClick: (String) -> Unit) {
    LazyRow {
        items(items, key = { it }) {
            TabItem(
                name = it,
                isSelected = selectedDate == it,
                onClick = { onItemClick(it) }
            )
        }
    }
}

@Composable
private fun TabItem(name: String, isSelected: Boolean, onClick: () -> Unit) {

    val background = if (isSelected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.surfaceContainer

    Card(
        colors = CardDefaults.cardColors(containerColor = background),
        modifier = Modifier.padding(8.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .height(38.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = name, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun ForecastItem(name: String, value: String) {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.LightMode, contentDescription = null)

            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
        )
    }
}

@Composable
private fun ForecastDayCard(
    items: ImmutableList<ForecastDayUiState>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        items.forEach {
            ForecastDayItem(uiState = it)
        }
    }
}

@Composable
private fun ForecastDayItem(uiState: ForecastDayUiState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = uiState.date,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = uiState.icon,
            contentDescription = null,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = uiState.tempC,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f)
        )
    }
}

@Composable
private fun ForecastHourCard(
    items: ImmutableList<ForecastHourUiState>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        LazyRow {
            items(items) {
                ForecastHourItem(uiState = it)
            }
        }
    }
}

@Composable
private fun ForecastHourItem(uiState: ForecastHourUiState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = uiState.tempC,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(
            modifier = Modifier.size(28.dp)
        )

        Icon(
            imageVector = uiState.icon,
            contentDescription = null,
        )

        Text(
            text = uiState.time,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun HomeTopBar(
    uiState: HomeUiState,
    darkTheme: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onSearchClick: () -> Unit,
    toggleDarkTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
    ) {
        Box(
            modifier = modifier
        ) {

            IconButton(
                onClick = onSearchClick,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }

            Text(
                text = uiState.location,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.TopCenter)

            )

            IconButton(
                onClick = toggleDarkTheme,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (darkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "DarkModeToggle"
                )
            }

            Text(
                text = uiState.currentTempC,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )

            Text(
                text = uiState.weatherDateTime,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )

            FilledIconButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                onClick = onRefresh
            ) {
                if (isRefreshing) CircularProgressIndicator(
                    Modifier.size(18.dp), strokeWidth = 2.dp, color = LocalContentColor.current
                )
                else Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
            }

        }
    }
}

