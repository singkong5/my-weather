package com.singkong.myweather.data

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.singkong.myweather.data.weather.Location
import com.singkong.myweather.data.weather.LocationDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var locationDao: LocationDao

    private val location1 = Location("San Francisco", "USA", 37.7749, -122.4194, 1)
    private val location2 = Location("New York", "USA", 40.7143, -74.006, 2)
    private val location3 = Location("Any City", "Any Country", 40.7143, -74.006, 3)

    private var rowId1: Long = -1
    private var rowId2: Long = -1
    private var rowId3: Long = -1

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        locationDao = database.locationDao()

        rowId1 = locationDao.insert(location1)
        rowId2 = locationDao.insert(location2)
        rowId3 = locationDao.insert(location3)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetCities() = runBlocking {
        val locList = locationDao.getSavedLocation().first()
        assertThat(locList.size, equalTo(2))
        assertNotEquals(rowId1, -1)
    }
}