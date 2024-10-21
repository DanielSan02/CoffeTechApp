package com.example.coffetech.view.flowering

import android.util.Log
import android.widget.Toast
import com.example.coffetech.viewmodel.flowering.MainFloweringViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.*
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.utils.SharedPreferencesHelper
import com.example.coffetech.view.common.HeaderFooterSubView


@Composable
fun MainFloweringView(
    navController: NavController,
    plotId: Int,
    viewModel: MainFloweringViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    // Obtener el contexto para acceder a SharedPreferences o cualquier otra fuente del sessionToken
    val context = LocalContext.current
    val sessionToken = remember { SharedPreferencesHelper(context).getSessionToken() }

    /*/ Llamar a loadFarmData y loadPlots cuando la vista se cargue
    LaunchedEffect(farmId) {
        sessionToken?.let {
            viewModel.loadFarmData(farmId, it, context)
            // Verificar permiso antes de cargar lotes
            if (viewModel.hasPermission("read_plots")) {
                viewModel.loadPlots(farmId, it)
            }
        } ?: run {
            viewModel.setErrorMessage("Session token no encontrado. Por favor, inicia sesión.")
        }
    }*/

    // Obtener los estados del ViewModel
    val tasks by viewModel.tasks.collectAsState()
    val start_date by viewModel.start_date.collectAsState()
    val end_date by viewModel.end_date.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val plotName by viewModel.plotName.collectAsState()
    val flowering_date by viewModel.flowering_date.collectAsState()

    val displayedPlotName = if (plotName.length > 21) {
        plotName.take(17) + "..." // Si tiene más de 21 caracteres, corta y añade "..."
    } else {
        plotName // Si es menor o igual a 21 caracteres, lo dejamos como está
    }


    // Vista principal
    HeaderFooterSubView(
        title = "Recomendaciones\nFloracion Principal",
        currentView = "Fincas",
        navController = navController,
        onBackClick = { navController.navigate("${Routes.PlotInformationView}/$plotId")  },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
        ) {
            // Contenido desplazable
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // Hacer la columna scrolleable verticalmente
            ) {
                /*if (isLoading) {
                    // Mostrar un indicador de carga
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cargando datos...",
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else if (errorMessage.isNotEmpty()) {
                    // Mostrar el error si ocurrió algún problema
                    Text(text = errorMessage, color = Color.Red)
                } else {*/

                // Mostrar el rol seleccionado
                Text(text = "Nombre de lote: ${displayedPlotName.ifEmpty { "Sin Nombre de lote" }}", color = Color.Black)
                Spacer(modifier = Modifier.height(16.dp))

                // Search bar para filtrar

                Text(text = "Fecha Actual: ${flowering_date.ifEmpty { "Sin Fecha" }}", color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de labores
                TasksList(
                    tasks = tasks,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                //}
            }
            // Botón flotante alineado al fondo derecho
            CustomFloatingActionButton(
                onAddClick = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainFloweringViewPreview() {
    CoffeTechTheme {
        MainFloweringView(
            navController = NavController(LocalContext.current),
            plotId = 1 // Valor simulado de farmId para la previsualización
        )
    }
}
