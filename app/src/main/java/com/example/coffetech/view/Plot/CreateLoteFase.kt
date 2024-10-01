package com.example.coffetech.view.Plot


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.VarietyCoffeeDropdown
import com.example.coffetech.viewmodel.Plot.CreateLoteFaseViewModel //

@Composable
fun CreateLoteFaseView(
    navController: NavController,
    viewModel: CreateLoteFaseViewModel = viewModel() //
) {

    // Estados para manejar los campos de texto
    var plotName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    // Obtener los estados del ViewModel
    val errorMessage by viewModel.errorMessage.collectAsState()
    val plotCoffeeVariety by viewModel.plotCoffeeVariety.collectAsState()
    val selectedVariety by viewModel.selectedVariety.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val hasChanges by viewModel.hasChanges.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))  // Fondo oscuro
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f) // Haz que el contenedor ocupe el 95% del ancho de la pantalla
                .background(Color.White, RoundedCornerShape(19.dp))
                .padding(horizontal = 20.dp, vertical = 90.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título de la vista
                Text(
                    text = "Crear Lote",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600,
                    fontSize = 25.sp,
                    color = Color(0xFF49602D),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Texto para "Fase de cultivo"
                Text(
                    text = "Fase de cultivo",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W200,
                    fontSize = 22.sp,
                    color = Color(0xFF000000),
                    modifier = Modifier.fillMaxWidth()
                )

                // Dropdown para la fase de cultivo
                VarietyCoffeeDropdown(
                    selectedVariety = selectedVariety,
                    onUnitChange = { viewModel.onPhaseChange(it) },
                    varieties = plotCoffeeVariety,
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para la Fecha de inicio
                ReusableTextField(
                    value = startDate,
                    onValueChange = {
                        startDate = it
                        viewModel.onStartDateChange(it) // Actualiza el ViewModel
                    },
                    placeholder = "Fecha de inicio",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para la Fecha final
                ReusableTextField(
                    value = endDate,
                    onValueChange = {
                        endDate = it
                        viewModel.onEndDateChange(it) // Actualiza el ViewModel
                    },
                    placeholder = "Fecha final (estimada)",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

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

                Spacer(modifier = Modifier.height(8.dp))

                // Botón Volver (verde claro)
                TextButton(
                    onClick = {
                        navController.popBackStack() // Regresar a la pantalla anterior
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Volver",
                        color = Color(0xFF49602D), // Color verde claro
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                }

                // Mostrar mensaje de error si lo hay
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateLoteFaseViewPreview() {
    CoffeTechTheme {
        CreateLoteFaseView(
            navController = NavController(LocalContext.current)
        )
    }
}


