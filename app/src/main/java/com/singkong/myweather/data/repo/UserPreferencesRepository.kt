package com.singkong.myweather.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.Calendar
import javax.inject.Inject

data class UserPreferences(
    val lastUpdatedTime: Long,
    val temperatureUnit: TemperatureUnit
)

enum class TemperatureUnit {
    FAHRENHEIT, CELSIUS
}

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val LAST_UPDATED = longPreferencesKey("last_updated")
        val TEMPERATURE_UNIT = stringPreferencesKey("temperature_unit")
    }

    val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            UserPreferences(preferences[PreferencesKeys.LAST_UPDATED] ?: 0,
                TemperatureUnit.valueOf(preferences[PreferencesKeys.TEMPERATURE_UNIT] ?: TemperatureUnit.CELSIUS.name))
        }

    suspend fun updateLastUpdated() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_UPDATED] = Calendar.getInstance().time.time
        }
    }

    suspend fun updateTemperatureUnit(unit: TemperatureUnit) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TEMPERATURE_UNIT] = unit.name
        }
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: UserPreferencesRepository? = null

        fun getInstance(dataStore: DataStore<Preferences>) =
            instance ?: synchronized(this) {
                instance ?: UserPreferencesRepository(dataStore).also { instance = it }
            }
    }
}