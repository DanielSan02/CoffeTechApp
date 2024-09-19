package com.example.coffetech.viewmodel.farm

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.model.GetFarmResponse
import com.example.coffetech.model.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



import android.util.Log // Importa la clase Log
import com.example.coffetech.utils.SharedPreferencesHelper

class FarmInformationViewModel : ViewModel() {

    // Estado de búsqueda
    private val _searchQuery = mutableStateOf("")
    val searchQuery: MutableState<String> = _searchQuery

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        Log.d("FarmInfoViewModel", "Search query changed: $query")
    }

        // Estados de nombre, área, y otros datos de la finca
    private val _farmName = MutableStateFlow("")
    val farmName: StateFlow<String> = _farmName.asStateFlow()

    private val _farmArea = MutableStateFlow(0.0)
    val farmArea: StateFlow<Double> = _farmArea.asStateFlow()

    private val _unitOfMeasure = MutableStateFlow("")
    val unitOfMeasure: StateFlow<String> = _unitOfMeasure.asStateFlow()

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status.asStateFlow()

    private val _selectedRole = MutableStateFlow("")
    val selectedRole: StateFlow<String> = _selectedRole.asStateFlow()

    private val _permissions = MutableStateFlow<List<String>>(emptyList())
    val permissions: StateFlow<List<String>> = _permissions.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _collaboratorName = MutableStateFlow("Colaborador de Ejemplo")
    val collaboratorName: StateFlow<String> = _collaboratorName.asStateFlow()

    fun onEditFarm(navController: NavController, farmId: Int, farmName: String, farmArea: Double, unitOfMeasure: String) {
        navController.navigate("FarmEditView/$farmId/$farmName/$farmArea/$unitOfMeasure")
    }


    fun onAddCollaborator(navController: NavController) {
        Log.d("FarmInfoViewModel", "Navigating to CollaboratorView")
        navController.navigate("CollaboratorView")
    }

    fun onAddLote(navController: NavController) {
        Log.d("FarmInfoViewModel", "Navigating to AddLoteView")
        navController.navigate("AddLoteView")
    }


    // Función para verificar si el rol tiene un permiso específico
    fun hasPermission(permission: String): Boolean {
        return _permissions.value.contains(permission)
    }

    // Función para cargar los permisos basados en el rol seleccionado
    fun loadRolePermissions(context: Context, selectedRoleName: String) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val roles = sharedPreferencesHelper.getRoles()

        // Buscar el rol seleccionado y obtener sus permisos
        roles?.find { it.name == selectedRoleName }?.let { role ->
            _permissions.value = role.permissions.map { it.name }
        }
    }

    /**
     * Loads the farm details from the backend by making an API request.
     *
     * @param farmId The ID of the farm to fetch details for.
     * @param sessionToken The session token for authorization.
     */
    fun loadFarmData(farmId: Int, sessionToken: String, context: Context) {
        if (sessionToken.isEmpty()) {
            setErrorMessage("Session token is missing. Please log in.")
            Log.e("FarmInfoViewModel", "Session token is missing. Aborting API call.")
            return
        }

        Log.d("FarmInfoViewModel", "Starting API call to fetch farm data for farmId: $farmId with sessionToken: $sessionToken")
        _isLoading.value = true

        // Llamada a Retrofit para obtener los datos de la finca
        RetrofitInstance.api.getFarm(farmId, sessionToken).enqueue(object : Callback<GetFarmResponse> {
            override fun onResponse(call: Call<GetFarmResponse>, response: Response<GetFarmResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("FarmInfoViewModel", "API call successful for farmId: $farmId")
                    response.body()?.data?.farm?.let { farm -> // Cambia aquí para acceder a farm dentro de data
                        _farmName.value = farm.name
                        _farmArea.value = farm.area // Convertir área a String con formato
                        _unitOfMeasure.value = farm.unit_of_measure
                        _selectedRole.value = farm.role
                        _status.value = farm.status
                        Log.d("FarmInfoViewModel", "Farm data loaded: $farm")
                        loadRolePermissions(context, farm.role)
                    } ?: run {
                        _errorMessage.value = "Error: Farm data is null"
                        Log.e("FarmInfoViewModel", "Error: Farm data is null")
                    }
                } else {
                    // Obtener el error detallado si hay uno
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Error: ${response.message()}, Details: $errorBody"
                    Log.e("FarmInfoViewModel", "Error in API response: ${response.message()}, Body: $errorBody")
                }
            }

            override fun onFailure(call: Call<GetFarmResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Error de conexión: ${t.message}"
                Log.e("FarmInfoViewModel", "API call failed for farmId: $farmId, Error: ${t.message}", t)
            }
        })
    }

    // Estado para la lista de lotes (esto parece ser datos simulados, podrías cambiarlo cuando trabajes con datos reales)
    private val _lotes = MutableStateFlow(
        listOf(
            "Lote 1" to "Descripción de Lote 1",
            "Lote 2" to "Descripción de Lote 2"
        )
    )

    val lotes: StateFlow<List<Pair<String, String>>> = _lotes.asStateFlow()

    /**
     * Sets an error message to be displayed in the UI.
     *
     * @param message The error message to set.
     */
    fun setErrorMessage(message: String) {
        _errorMessage.value = message
        Log.e("FarmInfoViewModel", "Error set: $message")
    }
}
