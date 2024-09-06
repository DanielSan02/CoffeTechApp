package com.example.coffetech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.coffetech.navigation.AppNavHost
import com.example.coffetech.ui.theme.CoffeTechTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeTechTheme {
                AppNavHost() // Inicializa la navegación de la aplicación aquí
            }
        }
    }
}
