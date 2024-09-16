package com.example.coffetech.viewmodel.farm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.navigation.NavController

class FarmEditViewModel : ViewModel() {

    // State de lso formularios
    private val _farmName = MutableStateFlow("Finca Los Álamos")
    val farmName: StateFlow<String> = _farmName.asStateFlow()

    private val _farmArea = MutableStateFlow("500 Ha")
    val farmArea: StateFlow<String> = _farmArea.asStateFlow()

    // State para unidad
    private val _selectedUnit = MutableStateFlow("Hectáreas")
    val selectedUnit: StateFlow<String> = _selectedUnit.asStateFlow()

    // Funcion para actualizar funcions
    fun onFarmNameChange(newName: String) {
        _farmName.value = newName
    }

    fun onFarmAreaChange(newArea: String) {
        _farmArea.value = newArea
    }

    fun onUnitChange(newUnit: String) {
        _selectedUnit.value = newUnit
    }

    // Esto es para guardar los cambios
    fun onSave(navController: NavController) {
        // Aquí puedes agregar la lógica para persistir los datos (API o Base de datos)
        navController.popBackStack() // Regresar a la vista anterior
    }

    // aQUI se elimina la finca
    fun onDelete(navController: NavController) {
        // Aquí puedes agregar la lógica de eliminación
        navController.popBackStack() // Regresar a la vista anterior tras la eliminación
    }
}
