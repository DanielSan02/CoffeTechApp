package com.example.coffetech.viewmodel.Collaborator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.example.coffetech.utils.SharedPreferencesHelper
import androidx.compose.runtime.State
import com.example.coffetech.model.ListCollaboratorResponse
import com.example.coffetech.model.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


data class Collaborator(
    val user_id: Int,
    val name: String,
    val email: String,
    val role: String, // Agrega también otras propiedades que necesitas
)


class CollaboratorViewModel : ViewModel() {

    // Lista original de colaboradores (sin filtrar)
    private val _allCollaborators = mutableListOf<Collaborator>()

    // Lista de colaboradores filtrada por la búsqueda
    private val _collaborators = MutableStateFlow<List<Collaborator>>(emptyList())
    val collaborators: StateFlow<List<Collaborator>> = _collaborators.asStateFlow()

    // Estado de búsqueda usando TextFieldValue
    private val _searchQuery = mutableStateOf(TextFieldValue(""))
    val searchQuery: MutableState<TextFieldValue> = _searchQuery


    private val _permissions = MutableStateFlow<List<String>>(emptyList())
    val permissions: StateFlow<List<String>> = _permissions.asStateFlow()

    // Estados de nombre, email, role y otros datos de colaborador

    private val _collaboratorName = MutableStateFlow("")
    val collaboratorName: StateFlow<String> = _collaboratorName.asStateFlow()

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


    private fun filterCollaboratorsByRole(role: String) {
        // Filtra las fincas según el rol seleccionado
        _collaborators.value = _allCollaborators.filter {
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
            // Si no se seleccionó ningún rol (es decir, "Todos los roles"), mostrar todos los colaboradores
            _collaborators.value = _allCollaborators
        } else {
            // Filtrar las fincas por el rol seleccionado
            filterCollaboratorsByRole(role)
        }
    }


    // Función para manejar el cambio del estado del menú desplegable
    fun setDropdownExpanded(isExpanded: Boolean) {
        _isDropdownExpanded.value = isExpanded
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


    // Función para cargar colaboradores desde el servidor
    fun loadCollaborators(context: Context, farmId: Int) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()

        if (sessionToken == null) {
            errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(
                context,
                "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        isLoading.value = true

        RetrofitInstance.api.listCollaborators(farmId, sessionToken)
            .enqueue(object : Callback<ListCollaboratorResponse> {
                override fun onResponse(
                    call: Call<ListCollaboratorResponse>,
                    response: Response<ListCollaboratorResponse>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            if (it.status == "success") {
                                val collaboratorsList = it.data.map { collaboratorResponse ->
                                    Collaborator(
                                        user_id = collaboratorResponse.user_id,
                                        name = collaboratorResponse.name,
                                        email = collaboratorResponse.email,
                                        role = collaboratorResponse.role
                                    )
                                }
                                Log.d("CollaboratorViewModel", "Lista de colaboradores recibida: $collaboratorsList")

                                _allCollaborators.clear()
                                _allCollaborators.addAll(collaboratorsList)
                                _collaborators.value =
                                    collaboratorsList // Mostrar todos los colaboradores al principio
                            } else {
                                errorMessage.value = it.message
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        errorMessage.value = "Error al obtener los colaboradores."
                        Log.e(
                            "CollaboratorViewModel",
                            "Error en la respuesta del servidor: ${response.errorBody()?.string()}"
                        )
                        Toast.makeText(
                            context,
                            "Error al obtener los colaboradores.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ListCollaboratorResponse>, t: Throwable) {
                    isLoading.value = false
                    errorMessage.value = "Error de conexión"
                    Log.e("CollaboratorViewModel", "Error de conexión")
                    Toast.makeText(context, "Error de conexión", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }


    // Función para manejar la búsqueda
    fun onSearchQueryChanged(query: TextFieldValue) {
        _searchQuery.value = query

        if (query.text.isBlank()) {
            // Si la búsqueda está vacía, mostramos todas las fincas
            _collaborators.value = _allCollaborators
        } else {
            // Filtramos las fincas según el nombre
            _collaborators.value = _allCollaborators.filter {
                it.name.contains(query.text, ignoreCase = true)
            }
        }
    }

    fun onEditCollaborator(
        navController: NavController,
        farmId: Int,
        collaboratorId: Int,
        collaboratorName: String,
        collaboratorEmail: String,
        selectedRole: String
    ) {
        navController.navigate(
            "EditCollaboratorView/$farmId/$collaboratorId/$collaboratorName/$collaboratorEmail/$selectedRole"
        )
    }
}
