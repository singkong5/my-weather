package com.singkong.myweather.data.place

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents a subset of Google geocode response from Google Maps API.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below.
 */
data class GoogleGeocodeResponse (
    @field:SerializedName("results") val results: List<GoogleGeocode>
)