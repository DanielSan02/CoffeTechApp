package com.example.coffetech.view.Plot


import PlotInformationViewModel
import android.content.ContentValues.TAG
import android.widget.Toast
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
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.*
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.utils.SharedPreferencesHelper
import com.example.coffetech.view.common.HeaderFooterSubView
import android.util.Log


@Composable
fun PlotInformationView(
    navController: NavController,
    plotId: Int,
    farmName: String,
    farmId: Int,
    viewModel: PlotInformationViewModel = viewModel()
) {


    val context = LocalContext.current
    val sessionToken = remember { SharedPreferencesHelper(context).getSessionToken() }

    // Obtener estados del ViewModel
    val plot by viewModel.plot.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val plotName by viewModel.plotName.collectAsState()
    val plotCoffeeVariety by viewModel.plotCoffeeVariety.collectAsState()
    val coordinatesUbication by viewModel.coordinatesUbication.collectAsState()
    val selectedVariety by viewModel.selectedVariety.collectAsState()
    val faseName by viewModel.faseName.collectAsState()
    val initialDate by viewModel.initialDate.collectAsState()
    val endDate by viewModel.endDate.collectAsState()
    Log.d(TAG, "Estados obtenidos del ViewModel: plotName=$plotName, plotCoffeeVariety=$plotCoffeeVariety")

    // Llamar a getPlot cuando el Composable se inicie
    LaunchedEffect(Unit) {
        if (sessionToken != null) {
            viewModel.getPlot(plotId, sessionToken)
        } else {
            viewModel.setErrorMessage("Token de sesión no encontrado.")
            // Opcional: Navegar al Login si el token no está disponible
            Toast.makeText(context, "Token de sesión no encontrado. Por favor, inicia sesión nuevamente.", Toast.LENGTH_LONG).show()
            navController.navigate(Routes.LoginView) {
                popUpTo(Routes.StartView) { inclusive = true }
            }
        }
    }

    // Mostrar indicadores de carga o mensajes de error
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (errorMessage.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = errorMessage, color = Color.Red, fontSize = 16.sp)
        }
    } else {
        plot?.let {
            // Main View
            HeaderFooterSubView(
                title = "Información de Lote",
                currentView = "Fincas",
                navController = navController,
                onBackClick = { navController.navigate("${Routes.FarmInformationView}/$farmId") },

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
                        plotCoffeeVariety = plotCoffeeVariety,
                        onEditClick = { viewModel.onEditPlot(
                            navController = navController,
                            plotId = plotId,
                            plotName = plotName,
                            selectedVariety = plotCoffeeVariety
                        ) },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val latitude =it.latitude
                    val longitude = it.longitude
                    val altitude = it.altitude
                    // Ubication Card
                    PlotUbicationCard(
                        latitude = latitude,
                        longitude = longitude,
                        altitude = altitude,
                        onEditClick = { navController.navigate("${Routes.EditMapPlotView}/$plotId/$latitude/$longitude/$altitude") },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ActionCard(
                        buttonText = "Floraciones",
                        onClick = {
                            viewModel.onFloracionesClick(navController, plotId)
                        },
                        modifier = Modifier
                            .width(198.dp)
                            .height(159.dp)
                            .padding(start = 2.5.dp)
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Text(
                        text = "Historial de detecciones",
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Implementa las tarjetas de historial de detecciones aquí
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotInformationViewPreview() {
    val navController = rememberNavController()
    CoffeTechTheme {
        PlotInformationView(
            navController = navController,
            plotId = 11,
            farmName = "Finca Ejemplo",
            farmId = 0
        )
    }
}
