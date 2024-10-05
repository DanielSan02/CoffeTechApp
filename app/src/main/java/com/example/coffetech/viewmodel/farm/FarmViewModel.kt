import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.example.coffetech.model.ListFarmResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import androidx.compose.runtime.State

import androidx.compose.runtime.mutableStateOf


data class Farm(
    val farm_id: Int, // Agrega farm_id como propiedad de la clase Farm
    val name: String,
    val area: Int, // Agrega también otras propiedades que necesitas
    val unit_of_measure: String,
    val role: String
)


class FarmViewModel : ViewModel() {

    // Lista original de fincas (sin filtrar)
    private val _allFarms = mutableListOf<Farm>()

    // Lista de fincas filtrada por la búsqueda
    private val _farms = MutableStateFlow<List<Farm>>(emptyList())
    val farms: StateFlow<List<Farm>> = _farms.asStateFlow()

    // Estado de búsqueda usando TextFieldValue
    private val _searchQuery = mutableStateOf(TextFieldValue(""))
    val searchQuery: MutableState<TextFieldValue> = _searchQuery

    // Estado para el rol seleccionado
    private val _selectedRole = mutableStateOf<String?>(null)
    val selectedRole = _selectedRole

    // Estado del menú de dropdown
    private val _isDropdownExpanded = mutableStateOf(false)
    val isDropdownExpanded = _isDropdownExpanded

    // Estado para la lista de roles
    private val _roles = MutableStateFlow<List<String>>(emptyList())
    val roles: StateFlow<List<String>> = _roles.asStateFlow()

    // Estado de carga
    val isLoading = mutableStateOf(false)

    // Error
    val errorMessage = mutableStateOf("")



    private fun filterFarmsByRole(role: String) {
        // Filtra las fincas según el rol seleccionado
        _farms.value = _allFarms.filter {
            it.role == role
        }
    }


    // Función para cargar los roles desde SharedPreferences
    fun loadRolesFromSharedPreferences(context: Context) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val roles = sharedPreferencesHelper.getRoles()?.map { it.name } ?: emptyList()
        _roles.value = roles
    }

    // Función para manejar la selección de roles
    fun selectRole(role: String?) {
        _selectedRole.value = role
        if (role == null) {
            // Si no se seleccionó ningún rol (es decir, "Todos los roles"), mostrar todas las fincas
            _farms.value = _allFarms
        } else {
            // Filtrar las fincas por el rol seleccionado
            filterFarmsByRole(role)
        }
    }


    // Función para manejar el cambio del estado del menú desplegable
    fun setDropdownExpanded(isExpanded: Boolean) {
        _isDropdownExpanded.value = isExpanded
    }

    // Función para cargar fincas desde el servidor
    fun loadFarms(context: Context) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()

        if (sessionToken == null) {
            errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(context, "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.", Toast.LENGTH_LONG).show()
            return
        }

        isLoading.value = true

        RetrofitInstance.api.listFarms(sessionToken).enqueue(object : Callback<ListFarmResponse> {
            override fun onResponse(call: Call<ListFarmResponse>, response: Response<ListFarmResponse>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.status == "success") {
                            val farmsList = it.data.farms.map { farmResponse ->
                                Farm(
                                    farm_id = farmResponse.farm_id,
                                    name = farmResponse.name,
                                    area = farmResponse.area.toInt(),  // Convertir a Int si es necesario
                                    unit_of_measure = farmResponse.unit_of_measure,
                                    role = farmResponse.role
                                )
                            }

                            _allFarms.clear()
                            _allFarms.addAll(farmsList)
                            _farms.value = farmsList // Mostrar todas las fincas al principio
                        } else {
                            errorMessage.value = it.message
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    errorMessage.value = "Error al obtener las fincas."
                    Log.e("FarmViewModel", "Error en la respuesta del servidor: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Error al obtener las fincas.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ListFarmResponse>, t: Throwable) {
                isLoading.value = false
                errorMessage.value = "Error de conexión"
                Log.e("FarmViewModel", "Error de conexión")
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })
    }

    // Función para manejar la búsqueda
    fun onSearchQueryChanged(query: TextFieldValue) {
        _searchQuery.value = query

        if (query.text.isBlank()) {
            // Si la búsqueda está vacía, mostramos todas las fincas
            _farms.value = _allFarms
        } else {
            // Filtramos las fincas según el nombre
            _farms.value = _allFarms.filter {
                it.name.contains(query.text, ignoreCase = true)
            }
        }
    }

    // Añade una propiedad para almacenar temporalmente la finca seleccionada
    private val _selectedFarmId = mutableStateOf<Int?>(null)
    val selectedFarmId: State<Int?> = _selectedFarmId

    fun onFarmClick(farm: Farm, navController: NavController) {
        // Guarda el ID de la finca seleccionada
        _selectedFarmId.value = farm.farm_id
        // Navega hacia la vista de información de la finca
        navController.navigate("FarmInformationView/${farm.farm_id}")
    }
}
