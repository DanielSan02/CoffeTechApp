package com.example.coffetech.viewmodel.PlotMap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlotViewModel : ViewModel() {

    // Estado para la ubicación seleccionada en el mapa (LatLng)
    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location.asStateFlow()

    // Estado para los permisos de localización
    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted: StateFlow<Boolean> = _locationPermissionGranted.asStateFlow()

    // Estado para los mensajes de error o validación
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Estado para controlar si se está guardando
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isFormSubmitted = mutableStateOf(false)
    val isFormSubmitted: State<Boolean> = _isFormSubmitted

    private val _latitude = MutableStateFlow("")
    val latitude: StateFlow<String> = _latitude.asStateFlow()

    private val _longitude = MutableStateFlow("")
    val longitude: StateFlow<String> = _longitude.asStateFlow()

   // Función para actualizar la ubicación del usuario (LatLng)
   fun onLocationChange(latLng: LatLng) {
       _location.value = latLng
       _latitude.value = latLng.latitude.toString()
       _longitude.value = latLng.longitude.toString()
   }

    // Función para verificar el estado de los permisos
    fun checkLocationPermission(context: Context): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        _locationPermissionGranted.value = isGranted
        return isGranted
    }

    // Función para actualizar el estado de los permisos
    fun updateLocationPermissionStatus(isGranted: Boolean) {
        _locationPermissionGranted.value = isGranted
    }

    fun onSubmit() {
        _isFormSubmitted.value = true
    }

    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun clearErrorMessage() {
        _errorMessage.value = ""
    }

    fun savePlotData() {
        // Lógica para guardar los datos
    }
}
