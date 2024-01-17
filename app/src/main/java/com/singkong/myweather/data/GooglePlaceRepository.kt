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

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: GooglePlaceRepository? = null

        fun getInstance(service: GooglePlacesService) =
            instance ?: synchronized(this) {
                instance ?: GooglePlaceRepository(service).also { instance = it }
            }
    }
}