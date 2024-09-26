package com.example.coffetech.view.PlotMap

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.res.painterResource
import androidx.activity.result.contract.ActivityResultContracts
import com.example.coffetech.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.UnitDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.PlotMap.PlotViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import android.app.Activity
import androidx.core.app.ActivityCompat
import android.content.Context
import androidx.compose.runtime.Composable
import kotlinx.coroutines.launch
import android.content.Intent
import android.net.Uri
import android.provider.Settings


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
    farmId: Int,
    viewModel: PlotViewModel = viewModel()
) {
    val context = LocalContext.current
    val location by viewModel.location.collectAsState()
    val locationPermissionGranted by viewModel.locationPermissionGranted.collectAsState()
    val plotRadiusValue = viewModel.plotRadius.collectAsState().value.toDoubleOrNull()
    val snackbarHostState = remember { SnackbarHostState() } // Estado para controlar el Snackbar
    val coroutineScope = rememberCoroutineScope()  // Para lanzar coroutines


    // Obtener los estados del ViewModel
    val plotRadius by viewModel.plotRadius.collectAsState()
    val selectedUnit by viewModel.selectedUnit.collectAsState()
    val areaUnits by viewModel.areaUnits.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Estado para saber si los permisos fueron denegados permanentemente
    var isPermissionDeniedPermanently by remember { mutableStateOf(false) }

    // Crear el lanzador de permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.updateLocationPermissionStatus(isGranted)
        } else {
            // Obtener la referencia de la Activity para verificar si los permisos fueron denegados permanentemente
            val activity = context as? Activity
            if (activity != null) {
                isPermissionDeniedPermanently = !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            }

            coroutineScope.launch {
                if (isPermissionDeniedPermanently) {
                    // Mostrar el Snackbar indicando que debe ir a la configuración
                    snackbarHostState.showSnackbar("Se necesitan permisos de localización")
                } else {
                    // Mostrar mensaje de que los permisos son necesarios y volver
                    snackbarHostState.showSnackbar("Se necesitan permisos de localización")
                    navController.popBackStack()  // Regresa a FarmInformationView
                }
            }


        }
    }


    // Lanzar permisos cada vez que el usuario pulsa el botón, sin importar si ya los denegó
    LaunchedEffect(Unit) {
        if (!viewModel.checkLocationPermission(context)) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Estructura de la UI usando Scaffold para manejar el Snackbar
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Manejar el Snackbar aquí
        content = { paddingValues ->  // Usar los valores de padding proporcionados
            Box(modifier = Modifier.padding(paddingValues)) {
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


//                                Spacer(modifier = Modifier.height(16.dp))
//
//                                // Campo para el radio del lote
//                                ReusableTextField(
//                                    value = viewModel.plotRadius.collectAsState().value,
//                                    onValueChange = { viewModel.onPlotRadiusChange(it) },
//                                    placeholder = "Radio del lote",
//                                    charLimit = 5,
//                                    modifier = Modifier.fillMaxWidth(),
//                                    isValid = viewModel.plotRadius.collectAsState().value.isNotEmpty() || !viewModel.isFormSubmitted.value,
//                                    errorMessage = if (viewModel.plotRadius.collectAsState().value.isEmpty() && viewModel.isFormSubmitted.value) "El radio no puede estar vacío" else ""
//                                )
//                                Spacer(modifier = Modifier.height(16.dp))
//                                // Unidad de medida
//                                UnitDropdown(
//                                    selectedUnit = selectedUnit,
//                                    onUnitChange = { viewModel.onUnitChange(it) },
//                                    units = areaUnits,
//                                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
//                                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//
//                                Spacer(modifier = Modifier.height(16.dp))
//
//                                // Mostrar mensaje de error si lo hay
//                                if (viewModel.errorMessage.collectAsState().value.isNotEmpty()) {
//                                    Text(text = viewModel.errorMessage.collectAsState().value, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
//                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Botón para guardar
                                ReusableButton(
                                    text = if (viewModel.isLoading.collectAsState().value) "Guardando..." else "Guardar",
                                    onClick = {
                                        viewModel.onSubmit()  // Marca que el formulario ha sido enviado
                                        viewModel.clearErrorMessage() // Limpiar mensaje de error anterior

                                        // Usa el valor obtenido arriba
                                        if (plotRadiusValue == null || plotRadiusValue <= 0 || plotRadiusValue > 10000) {
                                            viewModel.setErrorMessage("El radio debe ser un número mayor a 0 y menor a 10000.")
                                        } else if (selectedUnit.isEmpty()) {
                                            viewModel.setErrorMessage("Seleccione unidad de medida.")
                                        } else {
                                            viewModel.savePlotData()  // Llamar a la función si las validaciones pasan
                                        }
                                    },
                                    modifier = Modifier
                                        .size(width = 160.dp, height = 48.dp)
                                        .align(Alignment.CenterHorizontally),
                                    buttonType = ButtonType.Green,
                                    enabled = true // Siempre habilitado
                                )



                            }
                        }
                    }
                } else if (isPermissionDeniedPermanently) {
                    // Mostrar el botón para abrir la configuración si los permisos están denegados permanentemente
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Los permisos de localización fueron denegados. Habilítalos manualmente desde la configuración.",
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(
                            onClick = {
                                // Redirigir a la configuración de la aplicación
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Abrir Configuración")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón de "Volver" para regresar al FarmInformationView luego de cambiar los permisos en la configuración de la aplicación
                        ReusableTextButton(
                            navController = navController,
                            text = "Volver",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            destination = "FarmInformationView/$farmId"
                        )
                    }
                }
            }
        }
    )
}




@Preview(showBackground = true)
@Composable
fun AddLocationPlotPreview() {
    CoffeTechTheme {
        AddLocationPlot(
            navController = NavController(LocalContext.current),
            farmId = 1
        )
    }
}
