package com.example.coffetech.viewFinca

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.common.BottomNavigationBar
import com.example.coffetech.common.HamburgerMenu
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.TopBarWithHamburger
import com.example.coffetech.ui.theme.CoffeTechTheme

@Composable
fun FincaScreen(navController: NavController) {

    var isMenuVisible by remember { mutableStateOf(false) }
    val profileImage: Painter = painterResource(id = R.drawable.menu_icon)
    val profileName = "Usuario"
    

    Scaffold(
        topBar = {
            TopBarWithHamburger(
                onHamburgerClick = { isMenuVisible = !isMenuVisible },
                title = "Mis Fincas"
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = { navController.navigate("ruta de inicio") },
                onFincasClick = { /* Actual screen */ },
                onCentralButtonClick = { /* Action for central button */ },
                onReportsClick = { navController.navigate("ruta de reportes") },
                onCostsClick = { navController.navigate("ruta de costos") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
            if (isMenuVisible) {
                
                HamburgerMenu(
                    profileImage = profileImage,
                    profileName = profileName,
                    onProfileClick = { /* Navigate to profile screen */ },
                    onNotificationsClick = { /* Navigate to notifications screen */ },
                    onHelpClick = { /* Navigate to help screen */ },
                    onLogoutClick = { /* Handle logout */ }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEFEFEF))
                    .padding(16.dp)
            ) {
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
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FincaScreenPreview() {
    CoffeTechTheme {
        FincaScreen(navController = NavController(LocalContext.current))
    }
}