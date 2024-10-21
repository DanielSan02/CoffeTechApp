package com.example.coffetech.viewmodel.flowering

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class EditFloweringViewModel: ViewModel() {

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

    // Estado para rastrear si hay cambios pendientes
    private val _hasChanges = MutableStateFlow(false)
    val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

    // Guardar valores iniciales para comparación
    private var initialSelectedFloweringName = ""

    fun onFloweringDateChange(newDate: String) {
        if (_flowering_date.value != newDate) {
            _flowering_date.value = newDate
            _hasChanges.value = true
        }
    }

    // Función para manejar el cambio de rol
    fun onFloweringNameChange(newFlowering: String) {
        _selectedFloweringName.value = newFlowering
        checkForChanges()
    }


    // Verificar si hay cambios para habilitar/deshabilitar el botón de guardar
    private fun checkForChanges() {
        _hasChanges.value = _selectedFloweringName.value != initialSelectedFloweringName
    }

    fun editFlowering(context: Context, navController: NavController) {}

    fun deleteFlowering(context: Context, navController: NavController) {}
}