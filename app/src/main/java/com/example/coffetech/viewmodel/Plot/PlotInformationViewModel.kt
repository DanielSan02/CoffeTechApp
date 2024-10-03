package com.example.coffetech.viewmodel.Plot


import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.model.GetFarmResponse
import com.example.coffetech.model.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log // Importa la clase Log
import com.example.coffetech.utils.SharedPreferencesHelper

class PlotInformationViewModel : ViewModel() {

    // Estados de nombre, área, y otros datos de la finca
    private val _plotName = MutableStateFlow("")
    val plotName: StateFlow<String> = _plotName.asStateFlow()

    private val _plotCoffeeVariety = MutableStateFlow("")
    val plotCoffeeVariety: StateFlow<String> = _plotCoffeeVariety.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Estados de fase actual, nombre de fase , fecha inicial y fecha final
    private val _faseName = MutableStateFlow("")
    val faseName: StateFlow<String> = _faseName.asStateFlow()

    private val _initialDate = MutableStateFlow("")
    val initialDate: StateFlow<String> = _initialDate.asStateFlow()

    private val _endDate = MutableStateFlow("")
    val endDate: StateFlow<String> = _endDate.asStateFlow()

    // Estados de ubicacion de la finca
    private val _coordinatesUbication = MutableStateFlow("")
    val coordinatesUbication: StateFlow<String> = _coordinatesUbication.asStateFlow()

    // Función para editar la información del lote
    fun onEditPlot(navController: NavController, plotId: Int, plotName: String, plotCoffeeVariety: String) {
        navController.navigate("PlotEditView/$plotId/$plotName/$plotCoffeeVariety")
    }

    // Función para editar la fase
    fun onEditFase(navController: NavController, faseName: String, initialDate: String, endDate: String) {
        navController.navigate("PlotEditView/$faseName/$initialDate/$endDate")
    }

    // Función para editar la ubicación
    fun onEditUbication(navController: NavController, coordinatesUbication: String) {
        navController.navigate("PlotEditView/$coordinatesUbication")
    }

    // Función para manejar el click del botón "Floraciones"
    fun onFloracionesClick(navController: NavController, plotId: Int) {
        // Navega a la vista de Floraciones con el ID del lote
        navController.navigate("FloracionesView/$plotId")
    }

    // Nueva función para manejar el click del botón "Labores culturales"
    fun onLaboresCulturalesClick(navController: NavController, plotId: Int) {
        // Navega a la vista de Labores Culturales con el ID del lote
        navController.navigate("LaboresCulturalesView/$plotId")
    }

    // Función para cargar la información del lote (Ejemplo simplificado)
    fun loadPlotInformation(plotId: Int) {
        // Lógica para cargar la información del lote desde tu API u origen de datos
        _isLoading.value = true
        // Aquí reemplaza con la lógica real de carga de datos
        // Ejemplo de valores cargados
        _plotName.value = "Mi Lote"
        _plotCoffeeVariety.value = "Caturra"
        _faseName.value = "Fase 1"
        _initialDate.value = "2024-01-01"
        _endDate.value = "2024-06-01"
        _coordinatesUbication.value = "9.7489, -83.7534"
        _isLoading.value = false
    }
}
