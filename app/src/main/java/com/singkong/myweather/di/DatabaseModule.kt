package com.singkong.myweather.di

import android.content.Context
import com.singkong.myweather.data.AppDatabase
import com.singkong.myweather.data.weather.LocationDao
import com.singkong.myweather.data.weather.HourlyWeatherLogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideLocationDao(appDatabase: AppDatabase) : LocationDao {
        return appDatabase.locationDao()
    }

    @Provides
    fun provideHourlyWeatherLogDao(appDatabase: AppDatabase) : HourlyWeatherLogDao {
        return appDatabase.hourlyWeatherLogDao()
    }
}