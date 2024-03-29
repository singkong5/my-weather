package com.singkong.myweather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.singkong.myweather.data.weather.HourlyWeatherLog
import com.singkong.myweather.data.weather.HourlyWeatherLogDao
import com.singkong.myweather.data.weather.Location
import com.singkong.myweather.data.weather.LocationDao
import com.singkong.myweather.utilities.DATABASE_NAME

/**
 * The Room database for this app
 */
@Database(entities = [Location::class, HourlyWeatherLog::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
    abstract fun hourlyWeatherLogDao(): HourlyWeatherLogDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        //Create and pre-populate the database with 4 locations
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL("INSERT INTO location (name, city, country, latitude, longitude, state, user_order) VALUES ('San Francisco, CA, USA', 'San Francisco', 'US', '37.7749', '-122.4194', 'CA', '1')")
                        db.execSQL("INSERT INTO location (name, city, country, latitude, longitude, state, user_order) VALUES ('New York, NY, USA', 'New York', 'US', '40.7143', '-74.006', 'NY', '2')")
                        db.execSQL("INSERT INTO location (name, city, country, latitude, longitude, state, user_order) VALUES ('London, UK', 'London', 'England', '51.5085', '-0.1257', 'England', '3')")
                        db.execSQL("INSERT INTO location (name, city, country, latitude, longitude, state, user_order) VALUES ('Singapore', 'Singapore', 'SG', '1.2897', '103.8501', '', '4')")
                    }
                })
                .build()
        }
    }
}