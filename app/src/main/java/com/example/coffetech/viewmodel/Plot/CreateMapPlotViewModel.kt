package com.example.coffetech.viewmodel.Plot

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.WindowInsetsAnimation
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.CreateFarmResponse
import com.example.coffetech.model.CreatePlotRequest
import com.example.coffetech.model.OpenElevationService
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlotViewModel : ViewModel() {

    // Estado para la ubicación seleccionada en el mapa (LatLng)
    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location.asStateFlow()

    // Estado para la altitud
    private val _altitude = MutableStateFlow<Double?>(null)
    val altitude: StateFlow<Double?> = _altitude.asStateFlow()

    // Estado para los permisos de localización
    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted: StateFlow<Boolean> = _locationPermissionGranted.asStateFlow()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-elevation.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(OpenElevationService::class.java)

    // Estado para los mensajes de error o validación
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Estado para controlar si se está guardando
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isFormSubmitted = mutableStateOf(false)
    val isFormSubmitted: State<Boolean> = _isFormSubmitted

    private val _isAltitudeLoading = MutableStateFlow(false)
    val isAltitudeLoading: StateFlow<Boolean> = _isAltitudeLoading.asStateFlow()

    private val _latitude = MutableStateFlow("")
    val latitude: StateFlow<String> = _latitude.asStateFlow()

    private val _longitude = MutableStateFlow("")
    val longitude: StateFlow<String> = _longitude.asStateFlow()

    // Función para actualizar la ubicación del usuario (LatLng)
    fun onLocationChange(latLng: LatLng) {
        _location.value = latLng
        _latitude.value = latLng.latitude.toString()
        _longitude.value = latLng.longitude.toString()

        viewModelScope.launch {
            _isAltitudeLoading.value = true // Iniciar el estado de carga
            val elevation = fetchElevation(latLng)
            _altitude.value = elevation
            _isAltitudeLoading.value = false // Finalizar el estado de carga
        }
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

    suspend fun fetchElevation(latLng: LatLng): Double? {
        var attempts = 0
        var elevation: Double? = null

        while (attempts < 3 && elevation == null) {
            try {
                _errorMessage.value = ""
                val response = service.getElevation("${latLng.latitude},${latLng.longitude}")
                if (response.results.isNotEmpty()) {
                    elevation = response.results[0].elevation
                } else {
                    _errorMessage.value = "No se encontraron resultados."
                }
            } catch (e: Exception) {
                attempts++
                if (attempts >= 3) {
                    _errorMessage.value = "Error al obtener la altitud: ${e.message}"
                }
            }
        }
        return elevation
    }


    fun onCreatePlot(navController: NavController, context: Context, farmId: Int, plotName: String, coffeeVarietyName: String) {
        if (latitude.value.isBlank() || longitude.value.isBlank() || plotName.isBlank()) {
            _errorMessage.value = "Todos los campos deben estar completos."
            Toast.makeText(context, "Error: Todos los campos deben estar completos.", Toast.LENGTH_LONG).show()
            return
        }

        _isLoading.value = true

        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken() ?: run {
            _errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(context, "Error: No se encontró el token de sesión.", Toast.LENGTH_LONG).show()
            _isLoading.value = false
            return
        }

        val request = CreatePlotRequest(
            name = plotName,
            coffee_variety_name = coffeeVarietyName,
            latitude = latitude.value,
            longitude = longitude.value,
            altitude = altitude.value.toString(),
            farm_id = farmId
        )

        RetrofitInstance.api.createPlot(sessionToken, request).enqueue(object : retrofit2.Callback<CreateFarmResponse> {
            override fun onResponse(call: Call<CreateFarmResponse>, response: retrofit2.Response<CreateFarmResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.status == "success") {
                        Toast.makeText(context, "Lote creado exitosamente", Toast.LENGTH_LONG).show()
                        navController.navigate("farmInformationView/$farmId") {
                            popUpTo("farmInformationView/$farmId") { inclusive = true }
                        }
                    } else if (responseBody?.status == "error") {
                        val errorMsg = responseBody.message ?: "Error desconocido."
                        _errorMessage.value = errorMsg
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                    } else {
                        _errorMessage.value = "Respuesta inesperada del servidor."
                        Toast.makeText(context, "Respuesta inesperada del servidor.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    _errorMessage.value = "Error al crear el lote."
                    Toast.makeText(context, "Error al crear el lote.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CreateFarmResponse>, t: Throwable) {
                _isLoading.value = false
                val connectionErrorMsg = "Error de conexión"
                _errorMessage.value = connectionErrorMsg
                Toast.makeText(context, connectionErrorMsg, Toast.LENGTH_LONG).show()
            }
        })
    }



}
