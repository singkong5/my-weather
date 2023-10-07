package com.singkong.myweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.singkong.myweather.compose.WeatherListScreen
import com.singkong.myweather.data.LocationAndWeatherLogs
import com.singkong.myweather.ui.theme.MyWeatherTheme
import com.singkong.myweather.viewmodels.WeatherListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: WeatherListViewModel
) {
    val locations by viewModel.savedLocationList.observeAsState(initial = emptyList())
    val locationWeatherLogsMap by viewModel.locationWeatherLogsMap.observeAsState(initial = emptyMap())

    viewModel.refreshForecast(locations)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary),
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues),
        ) {
            val locationAndWeatherLogs = mutableListOf<LocationAndWeatherLogs>()
            for (loc in locations) {
                if (locationWeatherLogsMap.containsKey(loc)) {
                    locationAndWeatherLogs.add(LocationAndWeatherLogs(loc, locationWeatherLogsMap[loc]!!))
                } else {
                    locationAndWeatherLogs.add(LocationAndWeatherLogs(loc, emptyList()))
                }
            }
            WeatherListScreen(locationWeatherLogsList = locationAndWeatherLogs)
        }
    }
}
