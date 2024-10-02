package com.example.coffetech.viewmodel.Plot

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.coffetech.utils.SharedPreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.net.Uri

class CreatePlotInformationViewModel(
    private val savedStateHandle: SavedStateHandle // Agregamos SavedStateHandle
) : ViewModel() {

    // Usamos SavedStateHandle para mantener el estado
    private val _plotName = MutableStateFlow(savedStateHandle.get<String>("plotName") ?: "")
    val plotName: StateFlow<String> = _plotName.asStateFlow()

    private val _selectedVariety = MutableStateFlow(savedStateHandle.get<String>("selectedVariety") ?: "")
    val selectedVariety: StateFlow<String> = _selectedVariety.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _plotCoffeeVariety = MutableStateFlow<List<String>>(emptyList())
    val plotCoffeeVariety: StateFlow<List<String>> = _plotCoffeeVariety.asStateFlow()

    // Funciones para actualizar plotName y selectedVariety
    fun onPlotNameChange(newName: String) {
        _plotName.value = newName
        savedStateHandle["plotName"] = newName // Guardamos en SavedStateHandle
    }

    fun onVarietyChange(newVariety: String) {
        _selectedVariety.value = newVariety
        savedStateHandle["selectedVariety"] = newVariety // Guardamos en SavedStateHandle
    }

    // Cargar las variedades de café desde SharedPreferences o cualquier otra fuente
    fun loadCoffeeVarieties(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            val sharedPreferencesHelper = SharedPreferencesHelper(context)
            val varieties = sharedPreferencesHelper.getCoffeeVarieties() ?: listOf("Variedad 1", "Variedad 2")
            _plotCoffeeVariety.value = varieties
            _isLoading.value = false
        }
    }

    // Función para navegar a CreateMapPlotView
    fun saveAndNavigateToPlotMap(navController: NavController, farmId: Int) {
        if (_plotName.value.isBlank() || _selectedVariety.value.isBlank()) {
            _errorMessage.value = "Todos los campos son obligatorios"
            return
        }

        // Navegar pasando farmId, plotName y selectedVariety codificados en la URL
        navController.navigate(
            "createMapPlotView/$farmId/${Uri.encode(_plotName.value)}/${Uri.encode(_selectedVariety.value)}"
        )
    }
}
