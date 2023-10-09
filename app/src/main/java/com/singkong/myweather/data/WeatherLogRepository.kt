package com.singkong.myweather.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.singkong.myweather.api.WeatherService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherLogRepository @Inject constructor(private val hourlyWeatherLogDao: HourlyWeatherLogDao, private val service: WeatherService) {

    fun getTodayLocationAndWeatherLogs() : Flow<Map<Location, List<HourlyWeatherLog>>> {
        val start: Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        val end: Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant())
        return hourlyWeatherLogDao.loadLocationAndWeatherLogsByTimestamp(start, end)
    }

    @WorkerThread
    suspend fun refreshForecast(locationList: List<Location>) {

        val weatherLogList = mutableListOf<HourlyWeatherLog>()
        for (location in locationList) {
            val response = service.getWeatherForecast(location.latitude, location.longitude)
            response.hourly.time.zip(response.hourly.temperature).zip(response.hourly.weatherCode) { (timeStr, temperature), weatherCode ->
                //Combine list of hours, hourly temperature, hourly weather code to a list of HourlyWeatherLog
                weatherLogList.add(HourlyWeatherLog(location.locationId, SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(timeStr), temperature, weatherCode))
            }
        }

        hourlyWeatherLogDao.upsertAll(weatherLogList)
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: WeatherLogRepository? = null

        fun getInstance(hourlyWeatherLogDao: HourlyWeatherLogDao, service: WeatherService) =
            instance ?: synchronized(this) {
                instance ?: WeatherLogRepository(hourlyWeatherLogDao, service).also { instance = it }
            }
    }
}