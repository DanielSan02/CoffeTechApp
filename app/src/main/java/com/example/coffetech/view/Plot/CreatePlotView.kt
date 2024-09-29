package com.example.coffetech.view.Plot

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
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.UnitDropdown
import com.example.coffetech.common.VarietyCoffeeDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Plot.EditPlotInformationViewModel
import com.example.coffetech.viewmodel.Plot.CreatePlotViewModel

@Composable
fun CreatePlotView(
    navController: NavController,
    viewModel: CreatePlotViewModel = viewModel() // Cambiado a CreatePlotViewModel
) {
    val context = LocalContext.current

    // Obtener los estados del ViewModel
    val plotName by viewModel.plotName.collectAsState()
    val plotCoffeeVariety by viewModel.plotCoffeeVariety.collectAsState()
    val selectedVariety by viewModel.selectedVariety.collectAsState()
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
                .fillMaxWidth(0.85f) // Ajusta el ancho de la caja
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(horizontal = 20.dp, vertical = 30.dp) // Ajusta el padding
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                // Campo de texto para el nombre del lote
                Text(
                    text = "Nombre",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W200,
                    fontSize = 18.sp,
                    color = Color(0xFF000000),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Nombre de lote
                ReusableTextField(
                    value = plotName,
                    onValueChange = { viewModel.onPlotNameChange(it) }, // Actualiza el ViewModel
                    placeholder = "Nombre del lote",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para la variedad de café
                Text(
                    text = "Variedad de Café",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W200,
                    fontSize = 18.sp,
                    color = Color(0xFF000000),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Dropdown para seleccionar variedad de café
                VarietyCoffeeDropdown(
                    selectedVariety = selectedVariety,
                    onUnitChange = { viewModel.onVarietyChange(it) }, // Actualiza el ViewModel
                    varieties = plotCoffeeVariety,
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Mostrar mensaje de error si lo hay
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Guardar
                ReusableButton(
                    text = if (isLoading) "Guardando..." else "Siguiente",
                    onClick = { viewModel.savePhase() }, // Asignar acción de guardar
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp) // Ajuste de tamaño del botón
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Green, // Cambiar a un tipo de botón verde
                    enabled = hasChanges && !isLoading
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePlotViewPreview() {
    CoffeTechTheme {
        CreatePlotView(
            navController = NavController(LocalContext.current)
        )
    }
}
