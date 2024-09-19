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
import com.example.coffetech.common.LabeledTextField
import com.example.coffetech.common.ReusableButton
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
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(horizontal = 15.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .padding(top = 1.dp)
                    .offset(x = 255.dp, y = 4.dp)
            ) {
                BackButton(
                    navController = navController,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 18.dp)
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Información de la Finca",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600,
                    fontSize = 25.sp,
                    color = Color(0xFF49602D),
                )

                // Nombre de finca
                LabeledTextField(
                    label = "Nombre",
                    value = farmNameState,
                    onValueChange = { viewModel.onFarmNameChange(it) },
                    placeholder = "Nombre de la finca",
                    modifier = Modifier.fillMaxWidth()
                )

                // Área de la finca y unidad
                Column(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LabeledTextField(
                        label = "Área",
                        value = farmAreaState,
                        onValueChange = { viewModel.onFarmAreaChange(it) },
                        placeholder = "Área de la finca",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Unidad de medida
                    UnitDropdown(
                        selectedUnit = selectedUnit,
                        onUnitChange = { viewModel.onUnitChange(it) },
                        units = areaUnits,
                        expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                        arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    )
                }

                // Mostrar mensaje de error si lo hay
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Column(
                    modifier = Modifier
                        .padding(top = 25.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Botón para guardar
                    ReusableButton(
                        text = if (isLoading) "Guardando..." else "Guardar",
                        onClick = { viewModel.updateFarmDetails(farmId, navController, context) },
                        modifier = Modifier
                            .size(width = 120.dp, height = 40.dp)
                            .padding(vertical = 3.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF49602D),
                            contentColor = Color.White),
                        enabled = hasChanges && !isLoading // Habilitar solo si hay cambios y no está cargando
                    )
                }
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
