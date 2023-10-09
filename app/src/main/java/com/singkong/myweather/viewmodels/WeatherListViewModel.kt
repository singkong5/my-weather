package com.singkong.myweather.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.singkong.myweather.data.Location
import com.singkong.myweather.data.LocationRepository
import com.singkong.myweather.data.HourlyWeatherLog
import com.singkong.myweather.data.WeatherLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherLogRepository: WeatherLogRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    val savedLocationList: LiveData<List<Location>> = locationRepository.getSavedLocation().asLiveData()
    val locationWeatherLogsMap: LiveData<Map<Location, List<HourlyWeatherLog>>> = weatherLogRepository.getTodayLocationAndWeatherLogs().asLiveData()

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun refreshForecast(locationList: List<Location>) {

        if (locationList.isNotEmpty()) {
            _isLoading.value = true
            viewModelScope.launch {
                try {
                    weatherLogRepository.refreshForecast(locationList)
                    //delay(6000)
                    _isLoading.value = false
                } catch (exception: Exception) {
                    //TODO: Error handling
                    _isLoading.value = false
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