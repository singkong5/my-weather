package com.singkong.myweather.data

import com.google.gson.annotations.SerializedName

/**
* Data class that represents a weather forecast response from open-meteo.com.
*
* Not all of the fields returned from the API are represented here; only the ones used in this
* project are listed below. For a full list of fields, consult the API documentation
* [here](https://open-meteo.com/en/docs).
*/
data class WeatherServiceResponse(
    @field:SerializedName("latitude") val latitude: Double,
    @field:SerializedName("longitude") val longitude: Double,
    @field:SerializedName("hourly") val hourly: HourlyServiceResponse
)