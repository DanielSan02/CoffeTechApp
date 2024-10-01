package com.example.coffetech.viewmodel.Plot


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateLoteFaseViewModel : ViewModel() {

    // Estados de los campos de entrada
    private val _selectedPhase = MutableStateFlow("")
    val selectedPhase: StateFlow<String> = _selectedPhase.asStateFlow()

    private val _startDate = MutableStateFlow("")
    val startDate: StateFlow<String> = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow("")
    val endDate: StateFlow<String> = _endDate.asStateFlow()

    // Estado de carga y mensajes de error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Estado para rastrear si hay cambios pendientes
    private val _hasChanges = MutableStateFlow(false)
    val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    // Estado de variedades de café para el dropdown
    private val _plotCoffeeVariety = MutableStateFlow<List<String>>(emptyList())
    val plotCoffeeVariety: StateFlow<List<String>> = _plotCoffeeVariety.asStateFlow()

    private val _selectedVariety = MutableStateFlow("")
    val selectedVariety: StateFlow<String> = _selectedVariety.asStateFlow()

    // Función para actualizar la fase seleccionada
    fun onPhaseChange(newPhase: String) {
        _selectedPhase.value = newPhase
        _hasChanges.value = true
    }

    // Función para actualizar la fecha de inicio
    fun onStartDateChange(newStartDate: String) {
        _startDate.value = newStartDate
        _hasChanges.value = true
    }

    // Función para actualizar la fecha de finalización
    fun onEndDateChange(newEndDate: String) {
        _endDate.value = newEndDate
        _hasChanges.value = true
    }

    // Función para guardar los datos de la fase
    fun savePhase() {
        viewModelScope.launch {
            // Validar los campos antes de continuar
            if (_selectedPhase.value.isBlank() || _startDate.value.isBlank() || _endDate.value.isBlank()) {
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

    // Función para establecer las variedades de café disponibles (ejemplo)
    fun loadCoffeeVarieties() {
        // Esto simula la carga de variedades de café desde una fuente de datos
        _plotCoffeeVariety.value = listOf("Variedad 1", "Variedad 2", "Variedad 3")
    }
}
