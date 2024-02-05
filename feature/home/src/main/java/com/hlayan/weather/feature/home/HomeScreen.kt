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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import com.hlayan.weather.core.designsystem.R
import com.hlayan.weather.core.ui.common.EventEffect
import kotlinx.collections.immutable.ImmutableList
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScaffoldScope
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun HomeScreen(
    onSearchClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val toasterState = rememberToasterState()

    val userData by viewModel.userData.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val refreshing by viewModel.refreshing.collectAsStateWithLifecycle()
    val messageEvent by viewModel.messageEvent.collectAsStateWithLifecycle()

    EventEffect(event = messageEvent) {
        toasterState.show(it)
    }

    val collapsingScaffoldState = rememberCollapsingToolbarScaffoldState()

    HomeCollapsingScaffold(
        scaffoldState = collapsingScaffoldState,
        topBar = {
            HomeCollapsingTopBar(
                uiState = uiState,
                toolbarScaffoldState = collapsingScaffoldState,
                darkTheme = userData.darkTheme,
                refreshing = refreshing,
                onRefresh = viewModel::refreshWeather,
                onSearchClick = { onSearchClick(viewModel.locationName) },
                toggleDarkTheme = viewModel::toggleDarkTheme,
                paddings = it
            )
        }
    ) {
        HomeContent(uiState = uiState, paddings = it)
    }

    Toaster(
        state = toasterState,
        darkTheme = isSystemInDarkTheme(),
        alignment = Alignment.TopCenter
    )
}

@OptIn(ExperimentalToolbarApi::class)
@Composable
private fun HomeCollapsingScaffold(
    scaffoldState: CollapsingToolbarScaffoldState,
    topBar: @Composable CollapsingToolbarScope.(PaddingValues) -> Unit,
    content: @Composable CollapsingToolbarScaffoldScope.(PaddingValues) -> Unit
) {
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                scaffoldState.toolbarState.run {
                    when {
                        progress < 0.5f && progress != 0f -> collapse()
                        progress > 0.5f && progress != 1f -> expand()
                    }
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

    Scaffold { paddings ->
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = scaffoldState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = { topBar(paddings) }
        ) {
            Column(Modifier.nestedScroll(nestedScrollConnection)) {
                content(paddings)
            }
        }
    }
}

@Composable
private fun CollapsingToolbarScope.HomeCollapsingTopBar(
    uiState: HomeUiState,
    toolbarScaffoldState: CollapsingToolbarScaffoldState,
    darkTheme: Boolean,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    onSearchClick: () -> Unit,
    toggleDarkTheme: () -> Unit,
    paddings: PaddingValues
) {
    val itemSize = remember { mutableStateOf(IntSize(0, 0)) }
    val density = LocalDensity.current
    val progress = toolbarScaffoldState.toolbarState.progress

    Card(
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .parallax()
                .height(300.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = toolbarScaffoldState.toolbarState.progress
                }
                .padding(top = paddings.calculateTopPadding()),
        ) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.currentTempC,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 48.sp,
                    modifier = Modifier.padding(end = 16.dp)
                )

                AsyncImage(
                    model = uiState.weatherIcon,
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(56.dp)
                )
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { itemSize.value = it }
            .padding(top = paddings.calculateTopPadding())
    ) {
        IconButton(
            onClick = { onSearchClick() },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }

        Text(
            text = uiState.weatherDateTime,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.graphicsLayer {
                alpha = toolbarScaffoldState.toolbarState.progress
            }
        )

        IconButton(
            onClick = toggleDarkTheme,
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer {
                    alpha = toolbarScaffoldState.toolbarState.progress
                }
        ) {
            Icon(
                imageVector = if (darkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = "DarkModeToggle"
            )
        }
    }

    Box(
        modifier = Modifier
            .height(with(density) { itemSize.value.height.toDp() })
            .road(
                whenCollapsed = Alignment.Center,
                whenExpanded = Alignment.BottomStart
            )
            .padding(
                start = (progress * 16).dp,
                top = paddings.calculateTopPadding()
            )
    ) {
        Text(
            text = uiState.location,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Center)
        )
    }

    Box(
        modifier = Modifier
            .height(with(density) { itemSize.value.height.toDp() })
            .road(Alignment.TopEnd, Alignment.BottomEnd)
            .padding(top = paddings.calculateTopPadding())
            .padding(16.dp),
    ) {
        FilledIconButton(
            onClick = onRefresh
        ) {
            if (refreshing) CircularProgressIndicator(
                Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = LocalContentColor.current
            )
            else Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh"
            )
        }
    }
}

@Composable
private fun HomeContent(uiState: HomeUiState, paddings: PaddingValues) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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

        CurrentConditionGrid(
            uiState = uiState,
            modifier = Modifier.height(190.dp)
        )

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

@Composable
private fun CurrentConditionGrid(uiState: HomeUiState, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {

        item {
            ForecastItem(
                name = "Wind speed",
                value = uiState.wind,
                icon = painterResource(id = R.drawable.ic_wind)
            )
        }

        item {
            ForecastItem(
                name = "Humidity",
                value = uiState.humidity,
                icon = painterResource(id = R.drawable.ic_humidity)
            )
        }

        item {
            ForecastItem(
                name = "Pressure",
                value = uiState.pressure,
                icon = painterResource(id = R.drawable.ic_pressure)
            )
        }

        item {
            ForecastItem(
                name = "UV index",
                value = uiState.uvIndex,
                icon = painterResource(id = R.drawable.ic_uv_index)
            )
        }
    }
}

@Composable
private fun ForecastItem(name: String, value: String, icon: Painter) {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

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

        AsyncImage(
            model = uiState.icon,
            contentDescription = "Weather Icon",
            modifier = Modifier.size(36.dp)
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

        AsyncImage(
            model = uiState.icon,
            contentDescription = "Weather Icon",
            modifier = Modifier.size(36.dp)
        )

        Text(
            text = uiState.time,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}