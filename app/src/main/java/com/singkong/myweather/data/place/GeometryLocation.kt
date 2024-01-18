package com.singkong.myweather.data.place

import com.google.gson.annotations.SerializedName

data class GeometryLocation (
    @field:SerializedName("lat") val latitude: Double,
    @field:SerializedName("lng") val longitude: Double
)