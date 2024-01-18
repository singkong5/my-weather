package com.singkong.myweather.compose

import androidx.navigation.NamedNavArgument

sealed class Screen (
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {

    data object Home: Screen("home")

    data object AddLocation: Screen("newLocation")

}