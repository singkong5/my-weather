package com.singkong.myweather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.singkong.myweather.data.weather.Location
import com.singkong.myweather.data.repo.LocationRepository
import com.singkong.myweather.data.weather.LocationAndWeatherLogs
import com.singkong.myweather.data.repo.UserPreferences
import com.singkong.myweather.data.repo.UserPreferencesRepository
import com.singkong.myweather.data.repo.WeatherLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherLogRepository: WeatherLogRepository,
    private val locationRepository: LocationRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val userPreferences: LiveData<UserPreferences> = userPreferencesRepository.userPreferences.asLiveData()

    val locationWeatherLogsList: LiveData<List<LocationAndWeatherLogs>> =
        combine(locationRepository.getSavedLocation(), weatherLogRepository.getTodayLocationAndWeatherLogs()) {
            location, weatherLogsMap -> location.map {
                LocationAndWeatherLogs(location = it, hourlyWeatherLogs = weatherLogsMap[it] ?: emptyList())
            }
        }.asLiveData()

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun refreshForecast(locationList: List<Location>) {

        if (locationList.isNotEmpty()) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    weatherLogRepository.refreshForecast(locationList)
                    userPreferencesRepository.updateLastUpdated()
                    _isLoading.value = false
                } catch (exception: Exception) {
                    //TODO: Error handling
                    _isLoading.value = false
                }
            }
        } else {
            _isLoading.value = false
        }
    }

    fun addLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.insert(location)
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            //Run on background
            withContext(Dispatchers.IO) {
                locationRepository.delete(location)
            }
        }
    }
}