package com.example.coffetech.viewmodel.Collaborator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.content.Context
import androidx.navigation.NavController
import com.example.coffetech.utils.SharedPreferencesHelper
import androidx.compose.runtime.State


data class Collaborator(
    val collaborator_id: Int,
    val name: String,
    val role: String, // Agrega también otras propiedades que necesitas
    val id: String,
    val email: String,
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
            // Si no se seleccionó ningún rol (es decir, "Todos los roles"), mostrar todas las fincas
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


    private val _selectedCollaboratorId = mutableStateOf<Int?>(null)
    val selectedCollaboratorId: State<Int?> = _selectedCollaboratorId

    fun onCollaboratorClick(collaborator: Collaborator, navController: NavController) {
        // Guarda el ID de la finca seleccionada
        _selectedCollaboratorId.value = collaborator.collaborator_id
        // Navega hacia la vista de información de la finca
        navController.navigate("AddCollaboratorView/${collaborator.collaborator_id}")
    }
}
