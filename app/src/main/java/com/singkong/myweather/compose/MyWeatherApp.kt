package com.singkong.myweather.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.singkong.myweather.viewmodels.WeatherListViewModel

@Composable
fun MyWeatherApp(viewModel: WeatherListViewModel) {
    val navController = rememberNavController()
    MyWeatherNavHost(navController, viewModel)
}

@Composable
fun MyWeatherNavHost(navController: NavHostController, viewModel: WeatherListViewModel) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onFabClick = {
                    navController.navigate(Screen.AddLocation.route)
                }
            )
        }
        composable(route = Screen.AddLocation.route) {
            AddLocationScreen()
        }
    }
}