package com.example.coffetech.view.Auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.SearchBar
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterView

import com.example.coffetech.viewmodel.Auth.StartViewModel

@Composable
fun StartView(
    navController: NavController,
    viewModel: StartViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf(TextFieldValue("")) }

    // Aquí declaramos profileImage correctamente dentro de una función @Composable
    val profileImage: Painter = painterResource(id = R.drawable.menu_icon)



        // Llamamos a BaseScreen que contiene la lógica del top bar y bottom bar
        HeaderFooterView(
            title = "CoffeeTech",
            currentView = "Inicio",
            navController = navController

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF2F2F2)) // Aplica el fondo rojo al contenido central
                    .padding(16.dp) // Agrega padding si es necesario
            ) {
                // Contenido central
                // Agregar aqui composables
                Text(
                    text = "Contenido central",
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
                }
        }
    }


// Aquí usamos @Preview para previsualizar FarmView
@Preview(showBackground = true)
@Composable
fun StartViewPreview() {
    CoffeTechTheme {
        com.example.coffetech.view.Auth.StartView(navController = NavController(LocalContext.current))
    }
}
