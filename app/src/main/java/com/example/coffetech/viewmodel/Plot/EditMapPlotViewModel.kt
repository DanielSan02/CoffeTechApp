// EditMapPlotViewModel.kt
package com.example.coffetech.viewmodel.Plot

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.coffetech.model.*
import com.example.coffetech.utils.SharedPreferencesHelper
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class EditMapPlotViewModel : ViewModel() {

    private val _latitude = MutableStateFlow(0.0)
    val latitude: StateFlow<Double> = _latitude.asStateFlow()

    private val _longitude = MutableStateFlow(0.0)
    val longitude: StateFlow<Double> = _longitude.asStateFlow()

    private val _altitude = MutableStateFlow<Double?>(null)
    val altitude: StateFlow<Double?> = _altitude.asStateFlow()

    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted: StateFlow<Boolean> = _locationPermissionGranted.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess.asStateFlow()

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val _isAltitudeLoading = MutableStateFlow(false)
    val isAltitudeLoading: StateFlow<Boolean> = _isAltitudeLoading.asStateFlow()

    private val openElevationService: OpenElevationService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-elevation.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(OpenElevationService::class.java)
    }

    fun checkLocationPermission(context: Context): Boolean {
        val permissionState = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val granted = permissionState == PackageManager.PERMISSION_GRANTED
        _locationPermissionGranted.value = granted
        return granted
    }

    fun updateLocationPermissionStatus(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }

    fun onLocationChange(latLng: LatLng) {
        _latitude.value = latLng.latitude
        _longitude.value = latLng.longitude

        // Obtener altitud usando la API de Open Elevation
        fetchAltitude(latLng.latitude, latLng.longitude)
    }

    private fun fetchAltitude(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _isAltitudeLoading.value = true
            var attempts = 0
            var success = false

            while (attempts < 3 && !success) {
                try {
                    val locations = "$latitude,$longitude"
                    val elevationResponse = openElevationService.getElevation(locations)
                    if (elevationResponse.results.isNotEmpty()) {
                        _altitude.value = elevationResponse.results[0].elevation
                        success = true
                    } else {
                        _errorMessage.value = "No se pudo obtener la altitud."
                    }
                } catch (e: Exception) {
                    attempts++
                    if (attempts >= 3) {
                        _errorMessage.value = "Error al obtener la altitud: ${e.message}"
                    }
                }
            }

            _isAltitudeLoading.value = false
        }
    }




    fun setInitialLocation(latitude: Double, longitude: Double, altitude: Double) {
        _latitude.value = latitude
        _longitude.value = longitude
        _altitude.value = altitude
    }

    fun onUpdatePlotLocation(context: Context, plotId: Int) {
        _isLoading.value = true
        _errorMessage.value = ""

        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken() ?: run {
            _errorMessage.value = "No se encontró el token de sesión."
            _isLoading.value = false
            return
        }

        val request = UpdatePlotLocationRequest(
            plot_id = plotId,
            latitude = _latitude.value.toString(),
            longitude = _longitude.value.toString(),
            altitude = _altitude.value?.toString() ?: "0.0"
        )

        RetrofitInstance.api.updatePlotLocation(sessionToken, request).enqueue(object :
            Callback<UpdatePlotLocationResponse> {
            override fun onResponse(
                call: Call<UpdatePlotLocationResponse>,
                response: Response<UpdatePlotLocationResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.status == "success") {
                        _updateSuccess.value = true
                    } else {
                        _errorMessage.value = responseBody?.message ?: "Error desconocido."
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = errorBody ?: "Error al actualizar la ubicación."
                }
            }

            override fun onFailure(call: Call<UpdatePlotLocationResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Error de conexión: ${t.message}"
            }
        })
    }
}
