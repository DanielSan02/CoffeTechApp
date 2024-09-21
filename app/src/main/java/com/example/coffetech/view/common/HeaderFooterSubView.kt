    package com.example.coffetech.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.R
import com.example.coffetech.Routes.Routes
import com.example.coffetech.viewmodel.common.HeaderFooterViewModel


/**
 * Composable function that renders a layout with a top bar and bottom navigation bar.
 * The top bar includes a back arrow and a title, while the bottom bar contains multiple
 * navigation options. The central content is passed as a composable lambda.
 *
 * @param modifier A [Modifier] to adjust the layout or appearance of the view.
 * @param title The title to be displayed in the top bar.
 * @param navController The [NavController] used for navigation between screens.
 * @param currentView A string representing the current active view in the navigation bar.
 * @param content A lambda composable function that represents the main content of the screen.
 */
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.text.style.TextOverflow
import com.example.coffetech.common.ReusableTittleSmall

@Composable
fun HeaderFooterSubView(
    modifier: Modifier = Modifier,
    title: String,
    navController: NavController,
    currentView: String = "",
    content: @Composable () -> Unit
) {
    val headerFooterSubViewModel: HeaderFooterViewModel = viewModel()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .systemBarsPadding() // Aplica padding en las áreas de barras del sistema (notificaciones y navegación)
            .navigationBarsPadding() // Ajusta el padding para la barra de navegación
            .statusBarsPadding(), // Ajusta el padding para la barra de estado (notificaciones)
        topBar = {
            // Top bar with a back arrow and a title
            TopBarWithBackArrow(
                onBackClick = { navController.navigate(Routes.FarmView) },
                title = title,
                backgroundColor = Color.White,
            )
        },
        bottomBar = {
            // Bottom navigation bar with several navigation options
            BottomNavigationBar(
                currentView = currentView,
                navController = navController,
                onHomeClick = { headerFooterSubViewModel.onHomeClick(navController) },
                onFincasClick = { headerFooterSubViewModel.onFincasClick(navController) },
                onCentralButtonClick = { headerFooterSubViewModel.onCentralButtonClick(context) },
                onReportsClick = { headerFooterSubViewModel.onReportsClick(navController, context) },
                onCostsClick = { headerFooterSubViewModel.onCostsClick(navController, context) },
            )
        }
    ) { paddingValues ->
        // Main content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .systemBarsPadding() // Ajuste total para respetar las áreas seguras del sistema
        ) {
            content()
        }
    }
}


/**
 * Preview function for the HeaderFooterSubView.
 * It simulates the view with a top bar, bottom navigation bar, and content in a preview environment.
 */
@Preview(showBackground = true)
@Composable
fun HeaderFooterViewSubPreview() {
    val navController = rememberNavController()
    HeaderFooterSubView(
        title = "Nombre de finca",
        currentView = "Fincas",
        navController = navController
    ) {
        // Example content in the main area
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Contenido Principal")
        }
    }
}

/**
 * Composable function that renders a top bar with a back arrow and a title.
 *
 * @param onBackClick A lambda function that defines what happens when the back arrow is clicked.
 * @param title The title to be displayed in the top bar.
 * @param backgroundColor The background color for the top bar.
 * @param modifier A [Modifier] to adjust the layout or appearance of the top bar.
 */
@Composable
fun TopBarWithBackArrow(
    onBackClick: () -> Unit,
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)  // Ajustamos solo la altura
            .background(backgroundColor)
            .padding(horizontal = 10.dp)
    ) {
        // Back arrow button, alineado a la izquierda
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(56.dp) // Tamaño del área del botón de retroceso
        ) {
            Icon(
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = "Back",
                tint = Color(0xFF2B2B2B),
                modifier = Modifier.size(30.dp) // Tamaño del ícono de la flecha
            )
        }

        // Espaciador para el lado izquierdo
        Spacer(modifier = Modifier.weight(1f))

        // Título centrado
        Box(
            modifier = Modifier.weight(8f), // Ocupa más espacio central
            contentAlignment = Alignment.Center // Asegura que el texto esté centrado
        ) {
            ReusableTittleSmall(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Espaciador para el lado derecho, para equilibrar el diseño
        Spacer(modifier = Modifier.weight(1f))
    }
}




/**
 * Preview function for the TopBarWithBackArrow.
 * It simulates the top bar with a back arrow and a title in a preview environment.
 */
@Preview(showBackground = true)
@Composable
fun TopBarWithBackArrowPreview() {
    TopBarWithBackArrow(
        onBackClick = {},
        title = "Nombre de Finca",
        backgroundColor = Color.White
    )
}
