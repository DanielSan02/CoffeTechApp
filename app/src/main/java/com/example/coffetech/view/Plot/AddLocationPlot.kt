package com.example.coffetech.view.Plot

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.res.painterResource
import androidx.activity.result.contract.ActivityResultContracts
import com.example.coffetech.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.UnitDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Plot.PlotViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun GoogleMapView(
    location: LatLng, // Latitud y longitud inicial
    onLocationSelected: (LatLng) -> Unit // Callback para manejar el cambio de ubicación
) {
    // Estado de la posición de la cámara del mapa
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(location, 10f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng: LatLng ->
            onLocationSelected(latLng) // Actualizar la ubicación seleccionada
        }
    ) {
        Marker(
            state = MarkerState(position = location)
        )
    }
}




@Composable
fun AddLocationPlot(
    navController: NavController,
    viewModel: PlotViewModel = viewModel()
) {
    val context = LocalContext.current
    val location by viewModel.location.collectAsState()
    val locationPermissionGranted by viewModel.locationPermissionGranted.collectAsState()

    // Obtener los estados del ViewModel
    val plotRadius by viewModel.plotRadius.collectAsState()
    val selectedUnit by viewModel.selectedUnit.collectAsState()
    val areaUnits by viewModel.areaUnits.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Crear el lanzador de permisos de ubicación
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.updateLocationPermissionStatus(isGranted)
    }

    // Verificar permisos cuando se abre la vista
    LaunchedEffect(Unit) {
        if (!viewModel.checkLocationPermission(context)) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    if (locationPermissionGranted) {
        // Mostrar la vista principal solo si los permisos están concedidos
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101010)) // Fondo oscuro
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f) // Haz que el contenedor ocupe el 95% del ancho de la pantalla
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Botón de cerrar o volver (BackButton)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        BackButton(
                            navController = navController,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Título de la pantalla
                    Text(
                        text = "Crear Lote",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600,
                        fontSize = 25.sp,
                        color = Color(0xFF49602D),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Título para ubicación
                    Text(
                        text = "Ubicación",
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp,
                        color = Color(0xFF49602D),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostrar el mapa si los permisos están concedidos
                    GoogleMapView(
                        location = location ?: LatLng(0.0, 0.0), // Ubicación inicial predeterminada
                        onLocationSelected = { latLng ->
                            viewModel.onLocationChange(latLng) // Actualizar en el ViewModel
                        }
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo para el radio del lote
                    ReusableTextField(
                        value = viewModel.plotRadius.collectAsState().value,
                        onValueChange = { viewModel.onPlotRadiusChange(it) },
                        placeholder = "Radio del lote",
                        modifier = Modifier.fillMaxWidth(),
                        isValid = viewModel.plotRadius.collectAsState().value.isNotEmpty(),
                        errorMessage = if (viewModel.plotRadius.collectAsState().value.isEmpty()) "El radio no puede estar vacío" else ""
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Unidad de medida
                    UnitDropdown(
                        selectedUnit = selectedUnit,
                        onUnitChange = { viewModel.onUnitChange(it) },
                        units = areaUnits,
                        expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                        arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostrar mensaje de error si lo hay
                    if (viewModel.errorMessage.collectAsState().value.isNotEmpty()) {
                        Text(text = viewModel.errorMessage.collectAsState().value, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para guardar
                    ReusableButton(
                        text = if (viewModel.isLoading.collectAsState().value) "Guardando..." else "Guardar",
                        onClick = {
                            viewModel.savePlotData()
                        },
                        modifier = Modifier
                            .size(width = 160.dp, height = 48.dp)
                            .align(Alignment.CenterHorizontally),
                        buttonType = ButtonType.Red,
                        enabled = viewModel.plotRadius.collectAsState().value.isNotEmpty() && !viewModel.isLoading.collectAsState().value
                    )
                }
            }
        }
    } else {
        // Mostrar un mensaje indicando que los permisos son necesarios
        Text(
            text = "Se requieren permisos de ubicación para mostrar el mapa.",
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddLocationPlotPreview() {
    CoffeTechTheme {
        AddLocationPlot(
            navController = NavController(LocalContext.current)
        )
    }
}
