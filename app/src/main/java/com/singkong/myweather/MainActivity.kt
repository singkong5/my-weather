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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.singkong.myweather.compose.WeatherListScreen
import com.singkong.myweather.data.Location
import com.singkong.myweather.data.UserPreferences
import com.singkong.myweather.ui.theme.MyWeatherTheme
import com.singkong.myweather.viewmodels.WeatherListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition viewModel.isLoading.value == true
            }
            setOnExitAnimationListener { splashScreen ->
                // to remove splashscreen with no animation
                splashScreen.remove()
            }
        }
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
            WeatherListScreen(locationWeatherLogsList = locationAndWeatherLogsList, userPreferences, onDeleteLocation = {
                viewModel.deleteLocation(it)
            })
        }
    }
}
