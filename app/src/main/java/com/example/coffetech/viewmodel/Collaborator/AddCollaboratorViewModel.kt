package com.example.coffetech.viewmodel.Collaborator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.navigation.NavController

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.example.coffetech.model.CreateFarmRequest
import com.example.coffetech.model.CreateFarmResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddCollaboratorViewModel : ViewModel() {

    // Estados para los datos
    private val _collaboratorEmail = MutableStateFlow("")
    val collaboratorEmail: StateFlow<String> = _collaboratorEmail.asStateFlow()

    // Estado del menú de dropdown
    private val _isDropdownExpanded = mutableStateOf(false)
    val isDropdownExpanded = _isDropdownExpanded


    private val _collaboratorRole = MutableStateFlow<List<String>>(emptyList())
    val collaboratorRole: StateFlow<List<String>> = _collaboratorRole.asStateFlow()

    private val _selectedRole = MutableStateFlow("Seleccione un rol")
    val selectedRole: StateFlow<String> = _selectedRole.asStateFlow()

    var errorMessage = MutableStateFlow("")
        private set
    var isLoading = MutableStateFlow(false)
        private set

    fun onCollaboratorEmailChange(newName: String) {
        _collaboratorEmail.value = newName
    }

    fun onCollaboratorRoleChange(newRole: String) {
        _selectedRole.value = newRole
    }

    private fun validateInputs(): Boolean {
        if (_collaboratorEmail.value.isBlank()) {
            errorMessage.value = "El nombre del colaborador no puede estar vacío."
            return false
        }


        // Validación de la unidad seleccionada
        if (_selectedRole.value == "Seleccione un rol") {
            errorMessage.value = "Debe seleccionar una opción válida para el rol."
            return false
        }

        return true
    }


}


