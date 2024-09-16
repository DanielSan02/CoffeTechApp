package com.example.coffetech.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.coffetech.common.LargeText
import com.example.coffetech.viewmodel.common.HeaderFooterViewModel


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
        topBar = {
            TopBarWithBackArrow(
                onBackClick = { navController.navigate(Routes.FarmView)},
                title = title,
                backgroundColor = Color.White
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentView = currentView,
                navController = navController,
                onHomeClick = { headerFooterSubViewModel.onHomeClick(navController) },
                onFincasClick = { headerFooterSubViewModel.onFincasClick(navController) },
                onCentralButtonClick = { headerFooterSubViewModel.onCentralButtonClick(context) },
                onReportsClick = { headerFooterSubViewModel.onReportsClick(navController, context) },
                onCostsClick = { headerFooterSubViewModel.onCostsClick(navController, context) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HeaderFooterViewSubPreview() {
    val navController = rememberNavController()
    HeaderFooterSubView(
        title = "Nombre de finca",
        currentView = "Fincas",
        navController = navController
    ) {
        // Contenido de ejemplo
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Contenido Principal")
        }
    }
}

@Composable
fun TopBarWithBackArrow(
    onBackClick: () -> Unit,
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = "Back",
                    tint = Color(0xFF2B2B2B),
                    modifier = Modifier.size(24.dp)

                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarWithBackArrowPreview() {
    TopBarWithBackArrow(
        onBackClick = {},
        title = "Nombre de Finca",
        backgroundColor = Color.White
    )
}