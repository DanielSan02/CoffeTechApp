// FarmView.kt

package com.example.coffetech.view.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.coffetech.common.BaseScreen
import com.example.coffetech.common.BottomNavigationBar
import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.HamburgerMenu
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.SearchBar
import com.example.coffetech.common.TopBarWithHamburger
import com.example.coffetech.viewmodel.farm.FarmViewModel
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterView

// FarmView Function
@Composable
fun FarmView(
    navController: NavController,
    viewModel: FarmViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf(TextFieldValue("")) }

    // Aquí declaramos profileImage correctamente dentro de una función @Composable
    val profileImage: Painter = painterResource(id = R.drawable.menu_icon)

    // Llamamos a BaseScreen que contiene la lógica del top bar y bottom bar
    HeaderFooterView(
        title = "Mis Fincas",
        currentView = "Fincas",
        navController = navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
                .padding(16.dp)
        ) {
            SearchBar(
                query = query,
                onQueryChanged = { query = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Espacio para las fincas
            Text(
                text = "Aquí van las fincas",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ejemplo de fincas
            LazyColumn {
                items(listOf("Finca 1", "Finca 2", "Finca 3")) { finca ->
                    ReusableButton(
                        text = finca,
                        onClick = { /* Handle finca item click */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }

            FloatingActionButtonGroup(
                onMainButtonClick = { /* Handle main button click */ },
                onSubButton1Click = { /* Handle sub button 1 click */ },
                onSubButton2Click = { /* Handle sub button 2 click */ },
                subButton1Icon = painterResource(id = R.drawable.edit_icon), // Reemplaza con icono adecuado
                subButton2Icon = painterResource(id = R.drawable.plus_icon), // Reemplaza con icono adecuado
                mainButtonIcon = painterResource(id = R.drawable.plus_icon) // Reemplaza con icono adecuado
            )
        }
    }
}

// Aquí usamos @Preview para previsualizar FarmView
@Preview(showBackground = true)
@Composable
fun FarmViewPreview() {
    CoffeTechTheme {
        FarmView(navController = NavController(LocalContext.current))
    }
}
