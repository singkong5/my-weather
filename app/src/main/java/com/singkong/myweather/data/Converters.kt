package com.singkong.myweather.data

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converters to allow Room to reference Date type
 */
class Converters {

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun fromTimestamp(value: Long): Date = Date(value)
}