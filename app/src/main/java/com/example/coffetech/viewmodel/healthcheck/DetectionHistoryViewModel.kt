package com.example.coffetech.viewmodel.healthcheck

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.coffetech.viewmodel.Collaborator.Collaborator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class Checking(
    val checkingId: Int,
    val date: String,
    val nameCollaborator: String,
)

class DetectionHistoryViewModel: ViewModel() {

    // Lista original de colaboradores (sin filtrar)
    private val _allCheckings = mutableListOf<Checking>()

    // Lista de colaboradores filtrada por la búsqueda
    private val _checkings = MutableStateFlow<List<Checking>>(emptyList())
    val checkings: StateFlow<List<Checking>> = _checkings.asStateFlow()

    // Estado de búsqueda usando TextFieldValue
    private val _searchQuery = mutableStateOf(TextFieldValue(""))
    val searchQuery: MutableState<TextFieldValue> = _searchQuery

    private val _selectedDate = mutableStateOf<String?>(null)
    val selectedDate = _selectedDate

    // Estado para la lista de roles
    private val _dates = MutableStateFlow<List<String>>(emptyList())
    val dates: StateFlow<List<String>> = _dates.asStateFlow()

    // Estado del menú de dropdown
    private val _isDropdownExpanded = mutableStateOf(false)
    val isDropdownExpanded = _isDropdownExpanded

    // Estado de carga
    val isLoading = mutableStateOf(false)

    // Error
    val errorMessage = mutableStateOf("")
}