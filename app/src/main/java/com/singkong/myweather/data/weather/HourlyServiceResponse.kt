package com.singkong.myweather.data.weather

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents a subset of weather forecast response from open-meteo.com.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below. For a full list of fields, consult the API documentation
 * [here](https://open-meteo.com/en/docs).
 */
data class HourlyServiceResponse(
    @field:SerializedName("time") val time: List<String>,
    @field:SerializedName("temperature_2m") val temperature: List<Double>,
    @field:SerializedName("weathercode") val weatherCode: List<Int>
)
