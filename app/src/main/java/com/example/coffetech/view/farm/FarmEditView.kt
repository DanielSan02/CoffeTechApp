package com.example.coffetech.view.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.LabeledTextField
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.UnitDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.farm.FarmEditViewModel

@Composable
fun FarmEditView(
    navController: NavController,
    farmId: Int,
    farmName: String,
    farmArea: String,
    unitOfMeasure: String,
    viewModel: FarmEditViewModel = viewModel()
) {
    val context = LocalContext.current

    // Inicializar el ViewModel con los valores originales
    LaunchedEffect(Unit) {
        viewModel.initializeValues(farmName, farmArea, unitOfMeasure)
        viewModel.onFarmNameChange(farmName)
        viewModel.onFarmAreaChange(farmArea)
        viewModel.onUnitChange(unitOfMeasure)
        viewModel.loadUnitMeasuresFromSharedPreferences(context)
    }

    // Obtener los estados del ViewModel
    val farmNameState by viewModel.farmName.collectAsState()
    val farmAreaState by viewModel.farmArea.collectAsState()
    val selectedUnit by viewModel.selectedUnit.collectAsState()
    val areaUnits by viewModel.areaUnits.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val hasChanges by viewModel.hasChanges.collectAsState()

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
                    .padding(16.dp) // Añadir padding interno
            ) {
                // Botón de cerrar o volver (BackButton)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
                ) {
                    BackButton(
                        navController = navController,
                        modifier = Modifier.size(32.dp) // Tamaño más manejable
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Título de la pantalla
                Text(
                    text = "Información de la Finca",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600,
                    fontSize = 25.sp,
                    color = Color(0xFF49602D),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre de finca
                ReusableTextField(
                    value = farmNameState,
                    onValueChange = { viewModel.onFarmNameChange(it) },
                    placeholder = "Nombre de la finca",
                    modifier = Modifier.fillMaxWidth(), // Asegurar que ocupe todo el ancho disponible
                    isValid = farmNameState.isNotEmpty(),
                    errorMessage = if (farmNameState.isEmpty()) "El nombre de la finca no puede estar vacío" else ""
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Área de la finca y unidad
                ReusableTextField(
                    value = farmAreaState,
                    onValueChange = { viewModel.onFarmAreaChange(it) },
                    placeholder = "Área de la finca",
                    modifier = Modifier.fillMaxWidth(), // Asegurar que ocupe todo el ancho disponible
                    isValid = farmAreaState.isNotEmpty(),
                    errorMessage = if (farmAreaState.isEmpty()) "El área de la finca no puede estar vacía" else ""
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


                // Mostrar mensaje de error si lo hay
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar
                ReusableButton(
                    text = if (isLoading) "Guardando..." else "Guardar",
                    onClick = { // Recortar los valores antes de guardar
                        val trimmedFarmName = viewModel.farmName.value.trim()
                        val trimmedFarmArea = viewModel.farmArea.value.trim()

                        // Actualizar los valores recortados en el ViewModel
                        viewModel.onFarmNameChange(trimmedFarmName)
                        viewModel.onFarmAreaChange(trimmedFarmArea)

                        // Llamar al método de actualización con los valores ya recortados
                        viewModel.updateFarmDetails(farmId, navController, context)
                              },
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp) // Ajuste de tamaño del botón
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Red,
                    enabled = hasChanges && !isLoading
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun FarmEditViewPreview() {
    CoffeTechTheme {
        FarmEditView(
            navController = NavController(LocalContext.current),
            farmName = "Finca Ejemplo",
            farmId= 1,
            farmArea = "500 Ha",
            unitOfMeasure = "Hectáreas"
        )
    }
}
