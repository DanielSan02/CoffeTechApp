// En com.example.coffetech.viewmodel.Plot.EditPlotInformationViewModel

package com.example.coffetech.viewmodel.Plot

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.coffetech.model.*
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPlotInformationViewModel : ViewModel() {

    private val TAG = "EditPlotInfoViewModel"

    // Estados para los datos
    private val _plotId = MutableStateFlow(0)
    val plotId: StateFlow<Int> = _plotId.asStateFlow()

    private val _plotName = MutableStateFlow("")
    val plotName: StateFlow<String> = _plotName.asStateFlow()

    private val _selectedVariety = MutableStateFlow("")
    val selectedVariety: StateFlow<String> = _selectedVariety.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _plotCoffeeVariety = MutableStateFlow<List<String>>(emptyList())
    val plotCoffeeVariety: StateFlow<List<String>> = _plotCoffeeVariety.asStateFlow()

    private val _hasChanges = MutableStateFlow(false)
    val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

    // Funciones para actualizar plotName y selectedVariety
    fun onPlotNameChange(newName: String) {
        if (_plotName.value != newName) {
            _plotName.value = newName
            _hasChanges.value = true
        }
    }

    fun onVarietyChange(newVariety: String) {
        if (_selectedVariety.value != newVariety) {
            _selectedVariety.value = newVariety
            _hasChanges.value = true
        }
    }

    // Función para inicializar el ViewModel con los parámetros recibidos
    fun initialize(plotId: Int, plotName: String, selectedVariety: String, context: Context) {
        // Solo inicializar si el plotId actual es diferente al recibido
        if (_plotId.value != plotId) {
            _plotId.value = plotId
        }

        // Actualizar plotName y selectedVariety si son diferentes
        if (_plotName.value != plotName) {
            _plotName.value = plotName
        }

        if (_selectedVariety.value != selectedVariety) {
            _selectedVariety.value = selectedVariety
        }

        // Cargar variedades de café
        loadCoffeeVarieties(context)
    }

    // Cargar las variedades de café desde SharedPreferences o cualquier otra fuente
    private fun loadCoffeeVarieties(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val sharedPreferencesHelper = SharedPreferencesHelper(context)
                val varieties = sharedPreferencesHelper.getCoffeeVarieties() ?: listOf("Caturra", "Bourbon")
                _plotCoffeeVariety.value = varieties
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar variedades de café: ${e.message}"
                Toast.makeText(context, _errorMessage.value, Toast.LENGTH_LONG).show()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Función para guardar los cambios y realizar la llamada a la API
    fun saveChanges(
        plotId: Int,
        navController: NavController,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Validar entradas
        if (_plotName.value.isBlank() || _selectedVariety.value.isBlank()) {
            _errorMessage.value = "Todos los campos son obligatorios."
            Toast.makeText(navController.context, _errorMessage.value, Toast.LENGTH_LONG).show()
            onError(_errorMessage.value)
            return
        }

        // Obtener el session_token de SharedPreferences
        val sharedPreferencesHelper = SharedPreferencesHelper(navController.context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()
        if (sessionToken.isNullOrEmpty()) {
            _errorMessage.value = "Token de sesión no encontrado. Por favor, inicia sesión nuevamente."
            Toast.makeText(navController.context, _errorMessage.value, Toast.LENGTH_LONG).show()
            onError(_errorMessage.value)
            return
        }

        // Crear el objeto de solicitud
        val updateRequest = UpdatePlotGeneralInfoRequest(
            plot_id = plotId,
            name = _plotName.value,
            coffee_variety_name = _selectedVariety.value
        )

        _isLoading.value = true
        _errorMessage.value = ""

        // Realizar la llamada a la API
        RetrofitInstance.api.updatePlotGeneralInfo(sessionToken, updateRequest)
            .enqueue(object : Callback<UpdatePlotGeneralInfoResponse> {
                override fun onResponse(
                    call: Call<UpdatePlotGeneralInfoResponse>,
                    response: Response<UpdatePlotGeneralInfoResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            if (it.status == "success") {
                                Toast.makeText(navController.context, it.message, Toast.LENGTH_LONG).show()
                                _hasChanges.value = false
                                onSuccess()
                            } else {
                                _errorMessage.value = it.message
                                Toast.makeText(navController.context, it.message, Toast.LENGTH_LONG).show()
                                onError(it.message)
                            }
                        } ?: run {
                            _errorMessage.value = "Respuesta vacía del servidor."
                            Toast.makeText(navController.context, _errorMessage.value, Toast.LENGTH_LONG).show()
                            onError(_errorMessage.value)
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        errorBody?.let {
                            try {
                                val errorJson = JSONObject(it)
                                val errorMsg = if (errorJson.has("message")) {
                                    errorJson.getString("message")
                                } else {
                                    "Error desconocido al actualizar el lote."
                                }
                                _errorMessage.value = errorMsg
                                Toast.makeText(navController.context, errorMsg, Toast.LENGTH_LONG).show()
                                onError(errorMsg)
                            } catch (e: Exception) {
                                _errorMessage.value = "Error al procesar la respuesta del servidor."
                                Toast.makeText(navController.context, _errorMessage.value, Toast.LENGTH_LONG).show()
                                onError(_errorMessage.value)
                            }
                        } ?: run {
                            _errorMessage.value = "Respuesta vacía del servidor."
                            Toast.makeText(navController.context, _errorMessage.value, Toast.LENGTH_LONG).show()
                            onError(_errorMessage.value)
                        }
                    }
                }

                override fun onFailure(call: Call<UpdatePlotGeneralInfoResponse>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = "Error de conexión"
                    Toast.makeText(navController.context, _errorMessage.value, Toast.LENGTH_LONG).show()
                    onError(_errorMessage.value)
                }
            })
    }
}
