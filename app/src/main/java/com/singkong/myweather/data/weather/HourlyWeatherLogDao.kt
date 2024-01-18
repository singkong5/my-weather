package com.singkong.myweather.data.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.singkong.myweather.data.weather.HourlyWeatherLog
import com.singkong.myweather.data.weather.Location
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * The Data Access Object for the [HourlyWeatherLog] class.
 */
@Dao
interface HourlyWeatherLogDao {

    //Currently this query doesn't work. It doesn't give us HourlyWeatherLogs that are within the timestamp
    //@Query("SELECT DISTINCT location.id, location.name, location.country, location.latitude, location.longitude FROM hourly_weather_log INNER JOIN location ON hourly_weather_log.location_id = location.id WHERE hourly_weather_log.timestamp BETWEEN :start AND :end")
    //fun getLocationAndWeatherLogsByTimestamp(start: Calendar, end: Calendar): Flow<List<LocationAndWeatherLogs>>

    @Query("SELECT * FROM hourly_weather_log")
    fun loadHourlyWeatherLog(): Flow<List<HourlyWeatherLog>>

    @Query("SELECT * FROM location JOIN hourly_weather_log ON location.id = hourly_weather_log.location_id WHERE hourly_weather_log.timestamp >= :start AND hourly_weather_log.timestamp < :end")
    fun loadLocationAndWeatherLogsByTimestamp(start: Date, end: Date): Flow<Map<Location, List<HourlyWeatherLog>>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherLog: HourlyWeatherLog)

    @Upsert
    suspend fun upsertAll(weatherLogList: List<HourlyWeatherLog>)
}