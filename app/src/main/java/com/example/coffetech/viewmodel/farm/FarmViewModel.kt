package com.example.coffetech.viewmodel.farm

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
        // Implementa la lógica de búsqueda aquí
        println("Búsqueda: $query")
    }
}
