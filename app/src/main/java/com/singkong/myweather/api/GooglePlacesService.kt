package com.singkong.myweather.api

import com.singkong.myweather.BuildConfig
import com.singkong.myweather.data.place.GoogleGeocodeResponse
import com.singkong.myweather.data.place.GooglePredictionResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Used to connect to Google maps API to fetch cities based on input query and fetch geocode information
 */
interface GooglePlacesService {

    @GET("maps/api/place/autocomplete/json?types=%28cities%29")
    suspend fun getPredictions(@Query("input") input: String, @Query("key") key: String = BuildConfig.GOOGLE_API_KEY): GooglePredictionResponse

    @GET("maps/api/geocode/json")
    suspend fun getLatLong(@Query("place_id") placeId: String, @Query("key") key: String = BuildConfig.GOOGLE_API_KEY): GoogleGeocodeResponse

    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/"

        fun create(): GooglePlacesService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GooglePlacesService::class.java)
        }
    }
}