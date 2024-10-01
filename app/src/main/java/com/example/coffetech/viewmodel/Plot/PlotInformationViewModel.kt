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


    fun onEditPlot(navController: NavController, plotId: Int, plotName: String,plotCoffeeVariety: String) {
        navController.navigate("PlotEditView/$plotId/$plotName/$plotCoffeeVariety")
    }

    fun onEditFase(navController: NavController, faseName: String, initialDate: String, endDate: String) {
        navController.navigate("PlotEditView/$faseName/$initialDate/$endDate")
    }

    fun onEditUbication(navController: NavController, coordinatesUbication: String) {
        navController.navigate("PlotEditView/$coordinatesUbication")
    }

    // Nueva función para manejar el click del botón "Floraciones"
    fun onFloracionesClick(navController: NavController, plotId: Int) {
        // Navega a la vista de Floraciones con el ID del lote
        navController.navigate("FloracionesView/$plotId")
    }
}