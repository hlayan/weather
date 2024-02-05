package com.hlayan.weather.feature.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import com.hlayan.weather.core.model.SearchedLocation

@Composable
fun LocationScreen(
    onBackClick: () -> Unit,
    onLocationItemClick: (location: String, previousLocation: String?) -> Unit,
    viewModel: LocationViewModel = hiltViewModel()
) {
    val toasterState = rememberToasterState()

    val input by viewModel.input.collectAsStateWithLifecycle()
    val savedLocations by viewModel.savedLocations.collectAsStateWithLifecycle()
    val searchedLocations by viewModel.searchedLocations.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    val isInputEmpty by remember { derivedStateOf { input.isEmpty() } }

    Scaffold(
        topBar = {
            Column {
                SearchBar(
                    value = input,
                    isInputEmpty = isInputEmpty,
                    enableNavigationIcon = viewModel.previousLocation != null,
                    onValueChange = viewModel::onInputChange,
                    onNavigationIconClick = onBackClick,
                    onClearClick = viewModel::clearInput
                )
                HorizontalDivider()
            }
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            if (isInputEmpty) {
                if (savedLocations.isEmpty()) {
                    Text(
                        text = "There is no saved locations",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    SavedLocationList(
                        items = savedLocations,
                        onClickItem = {
                            onLocationItemClick(it.name, viewModel.previousLocation)
                        },
                        onRemoveItem = viewModel::onRemoveLocation
                    )
                }
            } else {
                if (searchedLocations.isEmpty()) {
                    if (!isRefreshing) {
                        Text(
                            text = "No results yet",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    SearchedLocationList(
                        items = searchedLocations,
                        savedItems = savedLocations,
                        onClickItem = {
                            onLocationItemClick(it.name, viewModel.previousLocation)
                        },
                        onSaveItem = viewModel::onSaveLocation
                    )
                }
            }
            if (isRefreshing) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    Toaster(state = toasterState)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    value: String,
    isInputEmpty: Boolean,
    enableNavigationIcon: Boolean,
    onValueChange: (String) -> Unit,
    onNavigationIconClick: () -> Unit,
    onClearClick: () -> Unit
) {
    TopAppBar(
        title = {
            if (isInputEmpty) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        navigationIcon = {
            if (enableNavigationIcon) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate up"
                    )
                }
            }
        },
        actions = {
            if (!isInputEmpty) {
                IconButton(onClick = onClearClick) {
                    Icon(Icons.Filled.Close, contentDescription = "Clean Input")
                }
            }
        },
    )
}

@Composable
private fun SavedLocationList(
    items: List<SearchedLocation>,
    onClickItem: (SearchedLocation) -> Unit,
    onRemoveItem: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Saved locations",
                style = MaterialTheme.typography.titleMedium
            )
        }
        items(items) {
            SavedLocationItem(
                name = "${it.name}, ${it.country}",
                onRemove = { onRemoveItem(it.id) },
                onClick = { onClickItem(it) }
            )
        }
    }
}

@Composable
private fun SearchedLocationList(
    items: List<SearchedLocation>,
    savedItems: List<SearchedLocation>,
    onClickItem: (SearchedLocation) -> Unit,
    onSaveItem: (SearchedLocation) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) {
            SearchedLocationItem(
                name = it.name,
                saved = it in savedItems,
                onSave = { onSaveItem(it) },
                onClick = { onClickItem(it) }
            )
        }
    }
}

@Composable
private fun SavedLocationItem(
    name: String,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            FilledIconButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            }

            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Outlined.RemoveCircleOutline,
                    contentDescription = "Remove"
                )
            }
        }
    }
}

@Composable
private fun SearchedLocationItem(
    name: String,
    saved: Boolean,
    onSave: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            FilledIconButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = name, modifier = Modifier.weight(1f))

            FilledIconButton(onClick = if (saved) onClick else onSave) {
                Icon(
                    imageVector = if (saved) Icons.AutoMirrored.Filled.ArrowForwardIos
                    else Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }
}
