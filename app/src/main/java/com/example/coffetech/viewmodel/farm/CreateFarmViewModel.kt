package com.example.coffetech.viewmodel.farm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.navigation.NavController

class CreateFarmViewModel : ViewModel() {

    // State de los formularios
    private val _farmName = MutableStateFlow("")
    val farmName: StateFlow<String> = _farmName.asStateFlow()

    private val _farmArea = MutableStateFlow("")
    val farmArea: StateFlow<String> = _farmArea.asStateFlow()

    // State para unidad
    private val _selectedUnit = MutableStateFlow("Metros cuadrados")
    val selectedUnit: StateFlow<String> = _selectedUnit.asStateFlow()

    // Funciones para actualizar valores
    fun onFarmNameChange(newName: String) {
        _farmName.value = newName
    }

    fun onFarmAreaChange(newArea: String) {
        _farmArea.value = newArea
    }

    fun onUnitChange(newUnit: String) {
        _selectedUnit.value = newUnit
    }

    // Crear la finca
    fun onCreate(navController: NavController) {
        // Aquí puedes agregar la lógica para guardar los datos de la nueva finca (API o Base de datos)
        navController.popBackStack() // Regresar a la vista anterior tras la creación
    }
}
