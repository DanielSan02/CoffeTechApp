// FarmView.kt

package com.example.coffetech.view.Auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.viewmodel.farm.FarmViewModel
import com.example.coffetech.ui.theme.CoffeTechTheme

@Composable
fun FarmView(

    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FarmViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Fincas",
            fontSize = 32.sp, // Tamaño grande para el texto
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FarmViewPreview() {
    CoffeTechTheme {
        FarmView(navController = NavController(LocalContext.current))
    }
}
