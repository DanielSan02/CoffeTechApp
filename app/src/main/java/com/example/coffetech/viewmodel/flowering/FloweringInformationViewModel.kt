package com.example.coffetech.viewmodel.flowering

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.coffetech.model.Flowering
import com.example.coffetech.model.Plot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FloweringInformationViewModel: ViewModel() {

    // Estado para tipo de floracion seleccionado
    private val _selectedFloweringName = mutableStateOf<String?>(null)
    val selectedFloweringName = _selectedFloweringName

    // Estado del menú de dropdown
    private val _isDropdownExpanded = mutableStateOf(false)
    val isDropdownExpanded = _isDropdownExpanded

    // Estados existentes
    private val _plotName = MutableStateFlow("")
    val plotName: StateFlow<String> = _plotName.asStateFlow()

    private val _flowering_type_name = MutableStateFlow("")
    val flowering_type_name: StateFlow<String> = _flowering_type_name.asStateFlow()

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status.asStateFlow()

    private val _flowering_date = MutableStateFlow("")
    val flowering_date: StateFlow<String> = _flowering_date.asStateFlow()

    // Lista completa de floraciones sin filtrar
    private val _allFlowerings = MutableStateFlow<List<Flowering>>(emptyList())

    // Lista de lotes filtrados según la búsqueda
    private val _filteredFlowerings = MutableStateFlow<List<Flowering>>(emptyList())
    val flowerings: StateFlow<List<Flowering>> = _filteredFlowerings.asStateFlow()

    // Estado para la lista de roles
    private val _flowerings_name = MutableStateFlow<List<String>>(emptyList())
    val flowerings_name: StateFlow<List<String>> = _flowerings_name.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Estado de búsqueda usando TextFieldValue
    private val _searchQuery = mutableStateOf(TextFieldValue(""))
    val searchQuery = _searchQuery


    // Función para manejar el cambio del estado del menú desplegable
    fun setDropdownExpanded(isExpanded: Boolean) {
        _isDropdownExpanded.value = isExpanded
    }

    // Función para manejar la selección de roles
    fun selectFloweringName(flowering_name: String?) {
        _selectedFloweringName.value = flowering_name
        //filterFarms() // Aplica el filtrado combinado
    }

    fun onSearchQueryChanged(query: TextFieldValue) {
        _searchQuery.value = query
        Log.d("FloweringInfoViewModel", "Search query changed: ${query.text}")
        filterFlowerings() // Filtra los lotes cada vez que cambia la consulta de búsqueda
    }

    /**
     * Filtra los lotes basados en la consulta de búsqueda.
     */
    private fun filterFlowerings() {
        val queryText = _searchQuery.value.text
        if (queryText.isEmpty()) {
            _filteredFlowerings.value = _allFlowerings.value
        } else {
            _filteredFlowerings.value = _allFlowerings.value.filter { flowering ->
                flowering.flowering_type_name.contains(queryText, ignoreCase = true)
            }
        }
    }

}