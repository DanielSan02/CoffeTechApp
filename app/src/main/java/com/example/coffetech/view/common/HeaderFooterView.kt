package com.example.coffetech.view.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.R
import com.example.coffetech.common.LargeText
import com.example.coffetech.viewmodel.common.HeaderFooterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffetech.utils.SharedPreferencesHelper



@Composable
fun HeaderFooterView(
    modifier: Modifier = Modifier,
    title: String,
    navController: NavController,
    currentView: String = "", //
    content: @Composable () -> Unit // No es necesario pasar el ViewModel aquí
) {
    // HeaderFooterViewModel ahora se maneja internamente
    val headerFooterViewModel: HeaderFooterViewModel = viewModel()
    val isMenuVisible by headerFooterViewModel.isMenuVisible.collectAsState()
    val context = LocalContext.current
    val sharedPreferencesHelper = SharedPreferencesHelper(context)
    val userName = sharedPreferencesHelper.getUserName()
// Obtener el nombre desde SharedPreferences

    Scaffold(
        topBar = {
            TopBarWithHamburger(
                onHamburgerClick = headerFooterViewModel::toggleMenu,
                title = title,
                backgroundColor = Color.White
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentView = currentView,
                navController = navController, // Pasar el NavController aquí
                onHomeClick = { headerFooterViewModel.onHomeClick(navController) },
                onFincasClick = { headerFooterViewModel.onFincasClick(navController) },
                onCentralButtonClick = {headerFooterViewModel.onCentralButtonClick(context)},
                onReportsClick = { headerFooterViewModel.onReportsClick(navController, context) },
                onCostsClick = { headerFooterViewModel.onCostsClick(navController, context) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content() // Pasamos el contenido dinámico aquí

            if (isMenuVisible) {
                HamburgerMenu(
                    profileImage = painterResource(id = R.drawable.menu_icon),
                    profileName = userName,
                    onProfileClick = { headerFooterViewModel.onProfileClick(navController) },
                    onNotificationsClick = headerFooterViewModel::onNotificationsClick,
                    onHelpClick = headerFooterViewModel::onHelpClick,
                    onLogoutClick = { headerFooterViewModel.onLogoutClick(context, navController) },
                    onCloseClick = headerFooterViewModel::toggleMenu
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderFooterViewPreview() {
    val navController = rememberNavController()
    HeaderFooterView(
        title = "Mi App",
        currentView = "Costos",
        navController = navController
    ) {
        // Contenido de ejemplo
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Contenido Principal")
        }
    }
}


@Composable
fun TopBarWithHamburger(
    onHamburgerClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 360.dp, height = 74.dp)
            .background(Color.White)
            .padding(10.dp)

    ) {
        // El texto centrado
        LargeText(
            text = title,
            fontSize = 20,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // El ícono alineado a la izquierda
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            IconButton(onClick = onHamburgerClick) {
                Icon(
                    painter = painterResource(R.drawable.menu_icon),
                    contentDescription = "Menu",
                    tint = Color(0xFF2B2B2B) ,
                            modifier = Modifier.size(30.dp)
                )
            }
        }
    }


}

@Composable
fun HamburgerMenu(
    profileImage: Painter,
    profileName: String,
    onProfileClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onHelpClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier

) {
    Box(
        modifier = modifier
            .width(280.dp)
            .fillMaxHeight()
            .background(Color.White)
            .border(1.dp, Color.LightGray, RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Botón de cierre
            IconButton(
                onClick = onCloseClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(R.drawable.close), // Asegúrate de tener este icono
                    contentDescription = "Cerrar menú",
                    tint = Color(0xFF2B2B2B)
                )
            }

            // Imagen de perfil y nombre
            Row(
                modifier = Modifier
                    .padding( 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = profileName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            Divider()

            // Opciones del menú
            MenuOption(
                icon = painterResource(R.drawable.user),
                text = "Perfil",
                onClick = onProfileClick
            )
            MenuOption(
                icon = painterResource(R.drawable.bell),
                text = "Notificaciones",
                onClick = onNotificationsClick
            )
            MenuOption(
                icon = painterResource(R.drawable.circle_question_regular),
                text = "Ayuda",
                onClick = onHelpClick
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón de cerrar sesión
            Button(
                onClick = onLogoutClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB31D34),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.logout),
                    contentDescription = "Cerrar sesión",
                    tint = Color.White

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión", color = Color.White)
            }
        }
    }
}

@Composable
private fun MenuOption(
    icon: Painter,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color(0xFF2B2B2B),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentView: String,
    navController: NavController,
    onHomeClick: () -> Unit,
    onFincasClick: () -> Unit,
    onCentralButtonClick: () -> Unit,
    onReportsClick: () -> Unit,
    onCostsClick: () -> Unit,
    modifier: Modifier = Modifier

) {
    BottomAppBar(
        modifier = modifier,
        containerColor = Color.White,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        IconButton(
            onClick = onHomeClick,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 9.dp)
                .size(70.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.home_icon),
                    contentDescription = "Inicio",
                    tint = if (currentView == "Inicio") Color(0xFFB31D34) else Color(0xFF9A9A9A), // Rojo si es la vista "Home"
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Inicio",
                    color = if (currentView == "Inicio") Color(0xFFB31D34) else Color(0xFF9A9A9A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }

        IconButton(
            onClick = onFincasClick,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
                .size(70.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.fincas_icon),
                    contentDescription = "Fincas",
                    tint = if (currentView == "Fincas") Color(0xFFB31D34) else Color(0xFF9A9A9A),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Fincas",
                    color = if (currentView == "Fincas") Color(0xFFB31D34) else Color(0xFF9A9A9A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        Box(
            modifier = Modifier
                .offset(y = -8.dp)
                .clip(CircleShape)
                .size(56.dp)
                .background(Color(0xFFB31D34))
                .clickable(onClick = onCentralButtonClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.central_icon),
                contentDescription = "Central Button",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }


        Spacer(modifier = Modifier.weight(0.5f))

        IconButton(
            onClick = onReportsClick,
            modifier = Modifier
                .weight(1f)
                .size(70.dp)
                .padding(vertical = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.reports_icon),
                    contentDescription = "Reportes",
                    tint = if (currentView == "Reportes") Color(0xFFB31D34) else Color(0xFF9A9A9A),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Reportes",
                    color = if (currentView == "Reportes") Color(0xFFB31D34) else Color(0xFF9A9A9A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }

        IconButton(
            onClick = onCostsClick,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
                .size(70.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.cost_icon),
                    contentDescription = "Costos",
                    tint = if (currentView == "Costos") Color(0xFFB31D34) else Color(0xFF9A9A9A),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Costos",
                    color = if (currentView == "Costos") Color(0xFFB31D34) else Color(0xFF9A9A9A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TopBarWithHamburgerPreview() {
    TopBarWithHamburger(
        onHamburgerClick = {},
        title = "Mi App",
        backgroundColor = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun HamburgerMenuPreview() {
    HamburgerMenu(
        profileImage = painterResource(id = R.drawable.user),
        profileName = "Usuario de Ejemplo",
        onProfileClick = {},
        onNotificationsClick = {},
        onHelpClick = {},
        onLogoutClick = {},
        onCloseClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(
        currentView= "Home",
        navController = navController,
        onHomeClick = {},
        onFincasClick = {},
        onCentralButtonClick = {},
        onReportsClick = {},
        onCostsClick = {}
    )
}
