package com.singkong.myweather.data

import com.google.gson.annotations.SerializedName

data class AddressComponent(
    @field:SerializedName("short_name") val name: String,
    @field:SerializedName("types") val types: List<String>
)
