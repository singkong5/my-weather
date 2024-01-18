package com.singkong.myweather.data.weather

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.singkong.myweather.data.weather.Location
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [Location] class.
 */
@Dao
interface LocationDao {

    @Query("SELECT * FROM location ORDER BY user_order")
    fun getSavedLocation(): Flow<List<Location>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(location: Location): Long

    @Upsert
    suspend fun upsertAll(stocks: List<Location>)

    @Delete
    suspend fun delete(location: Location)

    @Query("UPDATE location SET user_order = user_order - 1 WHERE user_order > :userOrder")
    fun updateUserOrderAfterDelete(userOrder: Int)

    @Query("UPDATE location SET user_order = user_order + 1")
    fun updateUserOrderAfterInsert()
}