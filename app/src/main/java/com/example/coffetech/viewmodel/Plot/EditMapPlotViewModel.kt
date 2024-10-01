package com.example.coffetech.viewmodel.Plot



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditMapPlotViewModel: ViewModel() {

    private val _radius = MutableLiveData<String>()
    val radius: LiveData<String> = _radius

    // Estado de carga y mensajes de error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Estado para rastrear si hay cambios pendientes
    private val _hasChanges = MutableStateFlow(false)
    val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage


    private val _areaUnits = MutableStateFlow<List<String>>(emptyList())
    val areaUnits: StateFlow<List<String>> = _areaUnits.asStateFlow()

    private val _selectedUnit = MutableStateFlow("Seleccione una opción")
    val selectedUnit: StateFlow<String> = _selectedUnit.asStateFlow()

    fun updateRadius(newRadius: String) {
        _radius.value = newRadius
    }


    fun onUnitChange(newUnit: String) {
        _selectedUnit.value = newUnit
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