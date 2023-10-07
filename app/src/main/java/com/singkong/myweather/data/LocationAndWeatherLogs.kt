package com.singkong.myweather.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between a [Location] and [HourlyWeatherLog], which is
 * used by Room to fetch the related entities.
 * However, since the query doesn't work, it's being used as UI model only for now.
 */
data class LocationAndWeatherLogs(
    @Embedded
    val location: Location,

    @Relation(parentColumn = "id", entityColumn = "location_id")
    val hourlyWeatherLogs: List<HourlyWeatherLog> = emptyList()
)
