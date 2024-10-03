package com.example.coffetech.view.Plot


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    plotName: String,
    coffeeVariety: String,
    selectedVariety: String,
    latitude: String,
    longitude: String,
    altitude: String,
    farmName: String,
    viewModel: PlotInformationViewModel = viewModel()
) {
    val context = LocalContext.current
    val sessionToken = remember { SharedPreferencesHelper(context).getSessionToken() }

    // Obtain the states from ViewModel

    val plotCoffeeVariety by viewModel.plotCoffeeVariety.collectAsState()
    val selectedVariety by viewModel.selectedVariety.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val faseName by viewModel.faseName.collectAsState()
    val initialDate by viewModel.initialDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()
    val coordinatesUbication by viewModel.coordinatesUbication.collectAsState()

    val displayedFarmName = if (plotName.length > 21) {
        plotName.take(17) + "..."
    } else {
        plotName
    }

    // Main View
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Finca: $farmName",
                    color = Color.Black,
                    maxLines = 3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )

                Spacer(modifier = Modifier.width(20.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // General Information Card
            GeneralPlotInfoCard(
                plotName = plotName,
                plotCoffeeVariety = coffeeVariety,
                onEditClick = { viewModel.onEditPlot(
                    navController= navController,
                    plotId = plotId,
                    plotName = plotName,
                    selectedVariety = selectedVariety
                ) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ubication Card
            PlotUbicationCard(
                latitude = latitude,
                longitude = longitude,
                altitude = altitude,
                onEditClick = { viewModel.onEditUbication(navController, coordinatesUbication) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            ActionCard(
                buttonText = "Floraciones", // Texto para el segundo botón
                onClick = {/*Navegacion a floraciones*/},
                modifier = Modifier
                    .width(198.dp)
                    .height(159.dp)
                    .padding(start = 2.5.dp)
            )


            // Spacer for spacing between button and detections history
            Spacer(modifier = Modifier.height(50.dp))

            // Título de la sección de historial de detecciones
            Text(
                text = "Historial de detecciones",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Aquí puedes agregar la implementación de las tarjetas de historial de detecciones
            // Como un Row o LazyRow para mostrar las tarjetas
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotInformationViewPreview() {
    CoffeTechTheme {
        PlotInformationView(
            navController = NavController(LocalContext.current),
            plotId=1,
            plotName="lote 1",
            coffeeVariety="caturro",
            selectedVariety = "caturro",
            latitude="",
            longitude="",
            altitude="",
            farmName="Hola",
        )
    }
}