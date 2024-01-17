package com.singkong.myweather.data

import com.google.gson.annotations.SerializedName

data class GoogleGeocodeResponse (
    @field:SerializedName("results") val results: List<GoogleGeocode>
)