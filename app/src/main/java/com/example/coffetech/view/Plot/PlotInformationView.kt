package com.example.coffetech.view.Plot


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.common.*
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.utils.SharedPreferencesHelper
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.viewmodel.Plot.PlotInformationViewModel

@Composable
fun PlotInformationView(
    navController: NavController,
    plotId: Int,
    viewModel: PlotInformationViewModel = viewModel()
) {
    // Obtener el contexto para acceder a SharedPreferences o cualquier otra fuente del sessionToken
    val context = LocalContext.current
    val sessionToken = remember { SharedPreferencesHelper(context).getSessionToken() }


    // Obtener los estados del ViewModel
    val plotName by viewModel.plotName.collectAsState()
    val plotCoffeeVariety by viewModel.plotCoffeeVariety.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val faseName by viewModel.faseName.collectAsState()
    val initialDate by viewModel.initialDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()
    val coordinatesUbication by viewModel.coordinatesUbication.collectAsState()


    val displayedFarmName = if (plotName.length > 21) {
        plotName.take(17) + "..." // Si tiene más de 13 caracteres, corta y añade "..."
    } else {
        plotName // Si es menor o igual a 13 caracteres, lo dejamos como está
    }


    // Vista principal
    HeaderFooterSubView(
        title = "Información de Lote",
        currentView = "Fincas",
        navController = navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
                .padding(16.dp)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically // Centra los elementos verticalmente
            ) {
                // Nombre alineado a la izquierda
                Text(
                    text = "Finca: Nombre de lote",
                    color = Color.Black,
                    maxLines = 3, // Limita a tres una línea
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Hace que el texto ocupe el espacio restante y se alinee a la derecha
                )

                Spacer(modifier = Modifier.width(20.dp)) // Espacio entre el botón y el texto
            }


            /*if (isLoading) {
                // Mostrar un indicador de carga
                CircularProgressIndicator()
                Text(
                    text = "Cargando datos de la finca...",
                    color =Color.Black,
                )
            } else if (errorMessage.isNotEmpty()) {
                // Mostrar el error si ocurrió algún problema
                Text(text = errorMessage, color = Color.Red)
            } else {*/


            Spacer(modifier = Modifier.height(16.dp))

            // Componente reutilizable de Información General
            GeneralPlotInfoCard(
                plotName = displayedFarmName,
                plotCoffeeVariety = plotCoffeeVariety,
                onEditClick = { viewModel.onEditPlot(navController, plotId, plotName, plotCoffeeVariety) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            PlotFaseCard(
                faseName = faseName,
                initialDate = initialDate,
                endDate = endDate,
                onEditClick = { viewModel.onEditFase(navController, faseName, initialDate, endDate) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            PlotUbicationCard(
                coordinatesUbication = coordinatesUbication,
                onEditClick = { viewModel.onEditUbication(navController, coordinatesUbication) },
            )

            //}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotInformationViewPreview() {
    CoffeTechTheme {
        PlotInformationView(
            navController = NavController(LocalContext.current),
            plotId = 1
        )
    }
}