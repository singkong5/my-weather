package com.singkong.myweather.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
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
}