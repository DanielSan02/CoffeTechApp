package com.example.coffetech.viewmodel.farm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FarmInformationViewModel : ViewModel() {


    // Estado de búsqueda
    private val _searchQuery = mutableStateOf("")
    val searchQuery: MutableState<String> = _searchQuery

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // Implementa la lógica de búsqueda aquí
        println("Búsqueda: $query")
    }

        fun onEditFarm(navController: NavController) {
            navController.navigate("FarmEditView")
        }

        fun onAddCollaborator(navController: NavController) {
            navController.navigate("CollaboratorView")
        }

        fun onAddLote(navController: NavController) {
            navController.navigate("AddLoteView")
        }

    // State para los nombres de la (finca, área y colaborador)
    private val _farmName = MutableStateFlow("Finca de Ejemplo")
    val farmName: StateFlow<String> = _farmName.asStateFlow()

    private val _farmArea = MutableStateFlow("Área de Ejemplo")
    val farmArea: StateFlow<String> = _farmArea.asStateFlow()

    private val _collaboratorName = MutableStateFlow("Colaborador de Ejemplo")
    val collaboratorName: StateFlow<String> = _collaboratorName.asStateFlow()

    // State para el rol seleccionado
    private val _selectedRole = MutableStateFlow("Administrador")
    val selectedRole: StateFlow<String> = _selectedRole.asStateFlow()

    // State la lista de lotes
    private val _lotes = MutableStateFlow(
        listOf(
            "Lote 1" to "Descripción de Lote 1",
            "Lote 2" to "Descripción de Lote 2"
        )
    )

    val lotes: StateFlow<List<Pair<String, String>>> = _lotes.asStateFlow()

    // Función que simula la obtención de datos de un endpoint
    fun fetchFarmData() {
        // Simluacion de endpoint
        // Por el momento son datos estaticos
        _farmName.value = "Finca Los Álamos"
        _farmArea.value = "500 Ha"
        _collaboratorName.value = "Juan Pérez"
        _selectedRole.value = "Administrador"

        // Actualizar los lotes simulados
        _lotes.value = listOf(
            "Lote del Sur" to "Descripcion del norte del sur",
            "Lote del Norte" to "Descripción sobre el Lote del Norte"
        )
    }
}

