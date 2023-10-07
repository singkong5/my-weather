package com.singkong.myweather.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.singkong.myweather.data.Location
import com.singkong.myweather.data.LocationRepository
import com.singkong.myweather.data.HourlyWeatherLog
import com.singkong.myweather.data.WeatherLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherLogRepository: WeatherLogRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    val savedLocationList: LiveData<List<Location>> = locationRepository.getSavedLocation().asLiveData()
    val locationWeatherLogsMap: LiveData<Map<Location, List<HourlyWeatherLog>>> = weatherLogRepository.getTodayLocationAndWeatherLogs().asLiveData()

    fun refreshForecast(locationList: List<Location>) {

        if (locationList.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    weatherLogRepository.refreshForecast(locationList)
                } catch (exception: Exception) {
                    Log.d("TAG", "Exception: $exception")
                }
            }
        }
    }

    fun addLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.insert(location)
        }
    }
}