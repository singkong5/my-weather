package com.singkong.myweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.singkong.myweather.compose.MyWeatherApp
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
                    MyWeatherApp(viewModel)
                }
            }
        }
    }
}


