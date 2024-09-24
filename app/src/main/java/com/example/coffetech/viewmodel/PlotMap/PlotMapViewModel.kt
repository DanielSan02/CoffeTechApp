package com.example.coffetech.viewmodel.PlotMap

import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlotViewModel : ViewModel() {
    // Estado para el radio del lote
    private val _plotRadius = MutableStateFlow("")
    val plotRadius: StateFlow<String> = _plotRadius.asStateFlow()

    // Estado para la unidad de medida (metros o kilómetros)
    private val _selectedUnit = MutableStateFlow("metros") // Valor inicial predeterminado
    val selectedUnit: StateFlow<String> = _selectedUnit.asStateFlow()

    private val _areaUnits = MutableStateFlow<List<String>>(listOf("metros", "kilómetros"))
    val areaUnits: StateFlow<List<String>> = _areaUnits.asStateFlow()

    // Estado para la ubicación seleccionada en el mapa (LatLng)
    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location.asStateFlow()

    // Estado para los permisos de localización
    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted: StateFlow<Boolean> = _locationPermissionGranted.asStateFlow()

    // Estado para los mensajes de error o validación
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    // Estado para saber si está en proceso de guardar o cargando
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Función para actualizar el radio del lote
    fun onPlotRadiusChange(newRadius: String) {
        _plotRadius.value = newRadius
    }

    // Función para actualizar la unidad de medida
    fun onUnitChange(newUnit: String) {
        _selectedUnit.value = newUnit
    }

    // Función para actualizar la ubicación del usuario (LatLng)
    fun onLocationChange(latLng: LatLng) {
        _location.value = latLng
    }

    // Función para verificar y actualizar el estado de los permisos de ubicación
    fun checkLocationPermission(context: Context): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        _locationPermissionGranted.value = isGranted
        return isGranted
    }

    // Actualizar el estado de los permisos
    fun updateLocationPermissionStatus(isGranted: Boolean) {
        _locationPermissionGranted.value = isGranted
    }

    // Guardar los detalles del lote
    fun savePlotData() {
        GlobalScope.launch {
            if (_plotRadius.value.isEmpty()) {
                _errorMessage.value = "El radio no puede estar vacío."
                return@launch
            }

            _isLoading.value = true

            // Simula una operación de guardado (podrías implementar tu lógica aquí)
            // Una vez terminado el guardado, puedes resetear los estados si es necesario
            _isLoading.value = false
            _errorMessage.value = ""
        }
    }
}
