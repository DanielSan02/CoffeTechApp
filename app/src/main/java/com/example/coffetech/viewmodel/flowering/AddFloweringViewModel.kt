package com.example.coffetech.viewmodel.flowering

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.utils.SharedPreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddFloweringViewModel: ViewModel() {

    // Estado para tipo de floracion seleccionado

    private val _flowering_date = MutableStateFlow("")
    val flowering_date: StateFlow<String> = _flowering_date.asStateFlow()

    private val _harvest_date = MutableStateFlow("")
    val harvest_date: StateFlow<String> = _harvest_date.asStateFlow()

    private val _floweringName = MutableStateFlow<List<String>>(emptyList())
    val floweringName: StateFlow<List<String>> = _floweringName.asStateFlow()

    private val _selectedFloweringName = MutableStateFlow("Seleccione una floracion")
    val selectedFloweringName: StateFlow<String> = _selectedFloweringName.asStateFlow()

    var errorMessage = MutableStateFlow("")
        private set
    var isLoading = MutableStateFlow(false)
        private set

    fun onFloweringDateChange(newDate: String) {
        _flowering_date.value = newDate
    }

    fun onFloweringNameChange(newFloweringName: String) {
        _selectedFloweringName.value = newFloweringName
    }

    fun onHarvestDateChange(newDate: String) {
        _harvest_date.value = newDate
    }

    /*fun loadNamesForFlowering(context: Context, userRole: String) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val roles = sharedPreferencesHelper.getRoles()

        // Buscar el rol del usuario y sus permisos
        roles?.find { it.name == userRole }?.let { role ->
            _permissions.value = role.permissions.map { it.name }

            // Determinar roles permitidos en función de permisos
            val allowedRoles = mutableListOf<String>()
            if (_permissions.value.contains("add_administrador_farm")) {
                allowedRoles.add("Administrador de finca")
            }
            if (_permissions.value.contains("add_operador_farm")) {
                allowedRoles.add("Operador de campo")
            }
            _collaboratorRole.value = allowedRoles
            Log.d("AddCollaboratorVM", "Roles disponibles según permisos: $allowedRoles")
        }
    }*/

    fun validateInputs(): Boolean {
        if (_flowering_date.value.isBlank()) {
            errorMessage.value = "La fecha de floracion no puede estar vacia."
            return false
        }

        // Validación del rol seleccionado
        if (_selectedFloweringName.value == "Seleccione una floracion") {
            errorMessage.value = "Debe seleccionar una floracion valida."
            return false
        }

        errorMessage.value = ""  // Limpiar el mensaje de error si no hay problemas
        return true
    }

    fun onCreate(navController: NavController, context: Context, farmId: Int) {
        if (!validateInputs()) {
            return
        }
    }
}