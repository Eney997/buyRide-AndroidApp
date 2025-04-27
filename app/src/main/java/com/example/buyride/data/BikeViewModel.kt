package com.example.buyride.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class BikeViewModel : ViewModel() {
    private val _bikes = MutableStateFlow<List<Bike>>(emptyList())
    val bikes: StateFlow<List<Bike>> = _bikes

    init {
        fetchBikes()
    }

    private fun fetchBikes() {
        viewModelScope.launch {
            try {
                _bikes.value = RetrofitInstance.api.getAllBikes()
            } catch (e: IOException) {
                // Handle network errors (e.g., no internet, server not running)
                Log.e("BikeViewModel", "Network error: ${e.localizedMessage}")
            } catch (e: Exception) {
                // Handle other types of exceptions (e.g., response errors, parsing errors)
                Log.e("BikeViewModel", "Error fetching bikes", e)
            }
        }
    }
}
