package com.singkong.myweather.data.repo

import androidx.annotation.WorkerThread
import com.singkong.myweather.data.weather.Location
import com.singkong.myweather.data.weather.LocationDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling [Location] data operations.
 *
 * Collecting from the Flows in [LocationDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class LocationRepository @Inject constructor(private val locationDao: LocationDao) {

    fun getSavedLocation() = locationDao.getSavedLocation()

    @WorkerThread
    suspend fun insert(location: Location) {
        locationDao.insert(location)
        //Fix user order after a location is inserted
        locationDao.updateUserOrderAfterInsert()
    }

    @WorkerThread
    suspend fun delete(location: Location) {
        val userOrder = location.userOrder
        locationDao.delete(location)
        //Fix user order after a location is deleted
        locationDao.updateUserOrderAfterDelete(userOrder)
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: LocationRepository? = null

        fun getInstance(locationDao: LocationDao) =
            instance ?: synchronized(this) {
                instance ?: LocationRepository(locationDao).also { instance = it }
            }
    }
}