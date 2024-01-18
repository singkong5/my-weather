package com.singkong.myweather.data

import androidx.annotation.WorkerThread
import com.singkong.myweather.api.GooglePlacesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GooglePlaceRepository @Inject constructor(private val service: GooglePlacesService) {

    @WorkerThread
    suspend fun getPredictions(input: String): List<GooglePlace> {

        val response = service.getPredictions(input)
        return response.predictions
    }

    @WorkerThread
    suspend fun getLocation(placeId: String): Location {
        val response = service.getLatLong(placeId)
        val place = response.results[0]

        var city = ""
        var country = ""
        var state = ""

        val addressComponents = place.addressComponents

        for (component in addressComponents) {
            if (component.types.contains("locality")) {
                city = component.name
            } else if (component.types.contains("country")) {
                country = component.name
            } else if (component.types.contains("administrative_area_level_1")) {
                state = component.name
            }
        }

        return Location(place.formattedAddress, city, country, place.geometry.location.latitude, place.geometry.location.longitude, state, 0)
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: GooglePlaceRepository? = null

        fun getInstance(service: GooglePlacesService) =
            instance ?: synchronized(this) {
                instance ?: GooglePlaceRepository(service).also { instance = it }
            }
    }
}