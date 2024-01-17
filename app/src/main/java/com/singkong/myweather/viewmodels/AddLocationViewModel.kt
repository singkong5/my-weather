package com.singkong.myweather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.singkong.myweather.BuildConfig
import com.singkong.myweather.data.GooglePlace
import com.singkong.myweather.data.GooglePlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(private val googlePlaceRepository: GooglePlaceRepository) : ViewModel() {

    private val _locationPredictions = MutableStateFlow<List<GooglePlace>?>(null)
    val locationPredictions: LiveData<List<GooglePlace>> = _locationPredictions.filterNotNull().asLiveData()

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: LiveData<String> = _searchQuery.asLiveData()

    fun onSearchTriggered(input: String) {
        if (input.length >= 3 && hasValidGoogleApiKey()) {
            viewModelScope.launch {
                try {
                    _locationPredictions.value = googlePlaceRepository.getPredictions(input)
                } catch (exception: Exception) {
                    //TODO: Error Handling
                }
            }
        }
    }
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            _locationPredictions.value = emptyList()
        }
    }

    fun hasValidGoogleApiKey() = (BuildConfig.GOOGLE_API_KEY != "null")
}