package com.singkong.myweather.di

import com.singkong.myweather.api.GooglePlacesService
import com.singkong.myweather.api.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService {
        return WeatherService.create()
    }

    @Singleton
    @Provides
    fun provideGooglePlacesService(): GooglePlacesService {
        return GooglePlacesService.create()
    }

}
