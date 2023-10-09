package com.singkong.myweather.api

import com.singkong.myweather.data.WeatherServiceResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Used to connect to open-meteo.com to fetch weather forecast
 */
interface WeatherService {

    @GET("forecast?hourly=temperature_2m,weathercode&forecast_days=3&past_days=1")
    suspend fun getWeatherForecast(@Query("latitude") lat: Double, @Query("longitude") long: Double): WeatherServiceResponse

    companion object {
        private const val BASE_URL = "https://api.open-meteo.com/v1/"

        fun create(): WeatherService {
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
                .create(WeatherService::class.java)
        }
    }
}