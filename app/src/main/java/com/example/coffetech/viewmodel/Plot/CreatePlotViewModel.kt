package com.example.coffetech.viewmodel.Plot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreatePlotViewModel : ViewModel() {

    // Estados de los campos de entrada
    private val _plotName = MutableStateFlow("")
    val plotName: StateFlow<String> = _plotName.asStateFlow()

    private val _plotCoffeeVariety = MutableStateFlow<List<String>>(emptyList())
    val plotCoffeeVariety: StateFlow<List<String>> = _plotCoffeeVariety.asStateFlow()

    private val _selectedVariety = MutableStateFlow("")
    val selectedVariety: StateFlow<String> = _selectedVariety.asStateFlow()

    // Estado de carga y mensajes de error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Estado para rastrear si hay cambios pendientes
    private val _hasChanges = MutableStateFlow(false)
    val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    init {
        loadCoffeeVarieties()
    }

    // Función para cargar variedades de café
    private fun loadCoffeeVarieties() {
        // Simula la carga de datos de variedades de café (reemplazar con lógica real de datos)
        _plotCoffeeVariety.value = listOf("Variedad 1", "Variedad 2", "Variedad 3")
    }

    // Función para actualizar el nombre del lote
    fun onPlotNameChange(newName: String) {
        _plotName.value = newName
        _hasChanges.value = true
    }

    // Función para actualizar la variedad seleccionada
    fun onVarietyChange(newVariety: String) {
        _selectedVariety.value = newVariety
        _hasChanges.value = true
    }

    // Función para guardar los datos del lote
    fun savePhase() {
        viewModelScope.launch {
            // Validar los campos antes de continuar
            if (_plotName.value.isBlank() || _selectedVariety.value.isBlank()) {
                _errorMessage.value = "Todos los campos son obligatorios"
                return@launch
            }

            // Resetear el mensaje de error
            _errorMessage.value = ""

            // Iniciar la carga
            _isLoading.value = true

            try {
                // Simula la operación de guardado (reemplazar con lógica real)
                simulateSaveOperation()

                // Marcar como éxito
                _errorMessage.value = "Datos guardados exitosamente"
                _hasChanges.value = false
            } catch (e: Exception) {
                // En caso de error, mostrar el mensaje correspondiente
                _errorMessage.value = "Error al guardar los datos"
            } finally {
                // Finalizar la carga
                _isLoading.value = false
            }
        }
    }




    // Simulación de una operación de guardado
    private suspend fun simulateSaveOperation() {
        // Simula un tiempo de espera para la operación
        kotlinx.coroutines.delay(2000L)
    }
}



