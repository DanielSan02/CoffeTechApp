package com.example.coffetech

import CommonDataViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    // Obtener la instancia del ViewModel usando la propiedad delegada by viewModels()
    private val commonDataViewModel: CommonDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Llamar a la función que verifica la versión y actualiza los datos si es necesario
        commonDataViewModel.updateDataIfVersionChanged(this)

        // Establecer el contenido de la interfaz de usuario utilizando Compose
        setContent {
            CoffeTechTheme {
                AppNavHost(context = this)
            }
        }
    }
}
