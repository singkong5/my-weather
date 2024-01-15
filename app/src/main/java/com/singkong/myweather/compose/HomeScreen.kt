package com.singkong.myweather.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.singkong.myweather.R
import com.singkong.myweather.data.Location
import com.singkong.myweather.data.UserPreferences
import com.singkong.myweather.viewmodels.WeatherListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WeatherListViewModel,
    onFabClick: () -> Unit = {}
) {
    val locationAndWeatherLogsList by viewModel.locationWeatherLogsList.observeAsState(initial = emptyList())
    val userPreferences by viewModel.userPreferences.observeAsState(initial = UserPreferences(0))

    val locationList = mutableListOf<Location>()
    for (item in locationAndWeatherLogsList) {
        locationList.add(item.location)
    }
    viewModel.refreshForecast(locationList)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary),
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onFabClick() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues),
        ) {
            WeatherListScreen(locationWeatherLogsList = locationAndWeatherLogsList, userPreferences, onDeleteLocation = {
                viewModel.deleteLocation(it)
            })
        }
    }
}