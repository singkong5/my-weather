package com.singkong.myweather.utilities

import com.singkong.myweather.R

/**
 * Helper functions to get weather icon and weather description based on the weather code.
 * Please check [here](https://open-meteo.com/en/docs) for weather variable documentation.
 */

fun getWeatherIconResourceId(weatherCode: Int): Int {
    return when (weatherCode) {
        0 -> R.drawable.ic_0
        1 -> R.drawable.ic_1
        2 -> R.drawable.ic_2
        3 -> R.drawable.ic_3
        45 -> R.drawable.ic_45
        48 -> R.drawable.ic_48
        51 -> R.drawable.ic_51
        53 -> R.drawable.ic_53
        55 -> R.drawable.ic_55
        56 -> R.drawable.ic_56
        57 -> R.drawable.ic_57
        61 -> R.drawable.ic_61
        63 -> R.drawable.ic_63
        65 -> R.drawable.ic_65
        71 -> R.drawable.ic_71
        73 -> R.drawable.ic_73
        75 -> R.drawable.ic_75
        77 -> R.drawable.ic_77
        80 -> R.drawable.ic_80
        81 -> R.drawable.ic_81
        82 -> R.drawable.ic_82
        85 -> R.drawable.ic_85
        86 -> R.drawable.ic_86
        95 -> R.drawable.ic_95
        96 -> R.drawable.ic_96
        99 -> R.drawable.ic_99
        else -> R.drawable.ic_default
    }
}

fun getWeatherDescriptionResourceId(weatherCode: Int): Int {
    return when (weatherCode) {
        0 -> R.string.clear_sky
        1 -> R.string.mainly_clear
        2 -> R.string.partly_cloudy
        3 -> R.string.overcast
        45, 48 -> R.string.fog
        51 -> R.string.light_drizzle
        53 -> R.string.moderate_drizzle
        55 -> R.string.heavy_drizzle
        56 -> R.string.light_freezing_drizzle
        57 -> R.string.heavy_freezing_drizzle
        61 -> R.string.light_rain
        63 -> R.string.moderate_rain
        65 -> R.string.heavy_rain
        71 -> R.string.light_snow
        73 -> R.string.moderate_snow
        75 -> R.string.heavy_snow
        77 -> R.string.snow_grains
        80 -> R.string.light_rain_showers
        81 -> R.string.moderate_rain_showers
        82 -> R.string.heavy_rain_showers
        85 -> R.string.light_snow_showers
        86 -> R.string.heavy_snow_showers
        95 -> R.string.thunderstorm
        96 -> R.string.thunderstorm_light_hail
        99 -> R.string.thunderstorm_heavy_hail
        else -> R.string.not_available
    }
}