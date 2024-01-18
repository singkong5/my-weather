package com.singkong.myweather.data

import com.google.gson.annotations.SerializedName

data class GoogleGeocode(
    @field:SerializedName("address_components") val addressComponents: List<AddressComponent>,
    @field:SerializedName("formatted_address") val formattedAddress: String,
    @field:SerializedName("geometry") val geometry: Geometry,
    @field:SerializedName("place_id") val placeId: String
)
