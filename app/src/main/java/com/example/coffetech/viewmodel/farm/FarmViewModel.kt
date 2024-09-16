package com.example.coffetech.viewmodel.farm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FarmViewModel : ViewModel() {

    // Lista de fincas
    private val _farms = MutableStateFlow<List<String>>(emptyList())
    val farms: StateFlow<List<String>> = _farms.asStateFlow()

    // Estado de búsqueda
    private val _searchQuery = mutableStateOf("")
    val searchQuery: MutableState<String> = _searchQuery

    // Estado de rol seleccionado
    private val _selectedRole = mutableStateOf<String?>(null)
    val selectedRole: MutableState<String?> = _selectedRole

    // Estado de menú expandido
    private val _isDropdownExpanded = mutableStateOf(false)
    val isDropdownExpanded: MutableState<Boolean> = _isDropdownExpanded

    init {
        loadFarms() // Cargar la lista de fincas al inicializar el ViewModel
    }

    // Carga de fincas simulada
    private fun loadFarms() {
        viewModelScope.launch {
            // Simula la carga de fincas desde una fuente de datos
            _farms.value = listOf("Finca 1", "Finca 2", "Finca 3")
        }
    }

    fun onFarmClick(farm: String) {
        // Lógica para manejar el clic en una finca
        println("Finca seleccionada: $farm")
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // Implementa la lógica de búsqueda aquí
        println("Búsqueda: $query")
    }

    fun selectRole(role: String) {
        _selectedRole.value = role
    }

    fun setDropdownExpanded(isExpanded: Boolean) {
        _isDropdownExpanded.value = isExpanded
    }
}

