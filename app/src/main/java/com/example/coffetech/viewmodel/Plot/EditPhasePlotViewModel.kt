package com.example.coffetech.viewmodel.Plot



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditPhasePlotViewModel : ViewModel() {

    // Estados de los campos de entrada
    private val _selectedPhase = MutableStateFlow("")
    val selectedPhase: StateFlow<String> = _selectedPhase

    private val _startDate = MutableStateFlow("")
    val startDate: StateFlow<String> = _startDate

    private val _endDate = MutableStateFlow("")
    val endDate: StateFlow<String> = _endDate

    // Estado de carga y mensajes de error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Estado para rastrear si hay cambios pendientes
    private val _hasChanges = MutableStateFlow(false)
    val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Función para actualizar la fase seleccionada
    fun onPhaseChange(newPhase: String) {
        _selectedPhase.value = newPhase
    }

    // Función para actualizar la fecha de inicio
    fun onStartDateChange(newStartDate: String) {
        _startDate.value = newStartDate
    }

    // Función para actualizar la fecha de finalización
    fun onEndDateChange(newEndDate: String) {
        _endDate.value = newEndDate
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

                simulateSaveOperation()

                // Marcar como éxito
                _errorMessage.value = "Datos guardados exitosamente"
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
