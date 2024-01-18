package com.singkong.myweather.data.place

import com.google.gson.annotations.SerializedName

data class GooglePlace(
    val description: String,
    @field:SerializedName("place_id") val placeId: String
)
