package com.singkong.myweather.data.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "location", indices = [Index(value = ["latitude", "longitude"], unique = true)])
data class Location(
    val name: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val state: String = "",
    @ColumnInfo(name = "user_order") val userOrder: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var locationId: Long = 0
}
