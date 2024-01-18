package com.singkong.myweather.data.place

/**
 * Data class that represents a subset of Google predictions response from Google Maps API.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below.
 */
data class GooglePredictionResponse(
    val predictions: List<GooglePlace>
)
