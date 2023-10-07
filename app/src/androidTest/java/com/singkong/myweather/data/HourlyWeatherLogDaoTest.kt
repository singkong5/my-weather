package com.singkong.myweather.data

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.singkong.myweather.utilities.testDate
import com.singkong.myweather.utilities.testDate1
import com.singkong.myweather.utilities.testDate2
import com.singkong.myweather.utilities.testDate3
import com.singkong.myweather.utilities.testDate4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Date

@RunWith(AndroidJUnit4::class)
class HourlyWeatherLogDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var locationDao: LocationDao
    private lateinit var hourlyWeatherLogDao: HourlyWeatherLogDao

    private val location1 = Location("San Francisco", "USA", 37.7749, -122.4194, 1)
    private val location2 = Location("New York", "USA", 40.7143, -74.006, 2)

    private var locId1: Long = -1
    private var locId2: Long = -1

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        locationDao = database.locationDao()
        hourlyWeatherLogDao = database.hourlyWeatherLogDao()

        locId1 = locationDao.insert(location1)
        locId2 = locationDao.insert(location2)

        val weatherLogSF1 = HourlyWeatherLog(locId1, testDate1, 17.7, 0)
        val weatherLogSF2 = HourlyWeatherLog(locId1, testDate2, 16.8, 1)
        val weatherLogSF3 = HourlyWeatherLog(locId1, testDate3, 15.0, 0)
        val weatherLogSF4 = HourlyWeatherLog(locId1, testDate4, 11.1, 3)

        val weatherLogNY1 = HourlyWeatherLog(locId2, testDate, 19.1, 0)
        val weatherLogNY2 = HourlyWeatherLog(locId2, testDate, 20.6, 1)
        val weatherLogNY3 = HourlyWeatherLog(locId2, testDate, 21.0, 3)
        val weatherLogNY4 = HourlyWeatherLog(locId2, testDate, 18.1, 3)

        hourlyWeatherLogDao.insert(weatherLogSF1)
        hourlyWeatherLogDao.insert(weatherLogSF2)
        hourlyWeatherLogDao.insert(weatherLogSF3)
        hourlyWeatherLogDao.insert(weatherLogSF4)

        hourlyWeatherLogDao.insert(weatherLogNY1)
        hourlyWeatherLogDao.insert(weatherLogNY2)
        hourlyWeatherLogDao.insert(weatherLogNY3)
        hourlyWeatherLogDao.insert(weatherLogNY4)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testLoadLocationAndWeatherLogsByTimestamp() = runBlocking {

        val testStartDate: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse("2023-09-04T00:00")
        val testEndDate: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse("2023-09-04T23:59")

        val locationWeatherLogsMap = hourlyWeatherLogDao.loadLocationAndWeatherLogsByTimestamp(testStartDate, testEndDate).first()
        assertThat(locationWeatherLogsMap.size, equalTo(1))

        val hourlyWeatherLogList = locationWeatherLogsMap.get(locationWeatherLogsMap.keys.first())
        assertThat(hourlyWeatherLogList!!.size, equalTo(3))

    }
}