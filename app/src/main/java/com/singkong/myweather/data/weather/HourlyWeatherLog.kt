package com.singkong.myweather.data.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.Date

/**
 * [HourlyWeatherLog] represents the forecast weather log in an hourly base (represented by the column temperature).
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "hourly_weather_log",
    foreignKeys = [
        ForeignKey(entity = Location::class, parentColumns = ["id"], childColumns = ["location_id"], onDelete = ForeignKey.CASCADE)
    ],
    primaryKeys = ["location_id", "timestamp"]
)
data class HourlyWeatherLog(
    @ColumnInfo(name = "location_id") val locationId: Long,
    @ColumnInfo(name = "timestamp") val timestamp: Date,
    /**
     * Temperature is in Celsius
     */
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "weather_code") val weatherCode: Int
)
