package com.example.coffetech.viewmodel.Plot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditMapPlotViewModel: ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    private val _radius = MutableLiveData<String>()
    val radius: LiveData<String> = _radius

    private val _unit = MutableLiveData<String>("") //
    val unit: LiveData<String> = _unit

    fun updateRadius(newRadius: String) {
        _radius.value = newRadius
    }

    fun updateUnit(newUnit: String) {
        _unit.value = newUnit
    }

    // Simulate saving the data to a repository
    fun saveLocation() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Check for validation
                if (_radius.value.isNullOrEmpty()) {
                    _errorMessage.value = "El radio no puede estar vacío."
                } else {
                    // Assume success
                    _errorMessage.value = ""
                    // Here you would normally call a repository to save the data
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al guardar la ubicación: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
