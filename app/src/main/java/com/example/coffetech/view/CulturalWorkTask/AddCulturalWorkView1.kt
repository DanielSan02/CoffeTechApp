package com.example.coffetech.view.CulturalWorkTask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.R
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.CulturalWorkTask.AddCulturalWorkViewModel1


@Composable
fun AddCulturalWorkView1(
    navController: NavController,
    farmId: Int,
    plotName: String = "",
    selectedVariety: String = "",
    viewModel: AddCulturalWorkViewModel1 = viewModel()
) {
    val flowering_date by viewModel.flowering_date.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val typeCulturalWork by viewModel.typeCulturalWork.collectAsState()
    val isFormSubmitted by viewModel.isFormSubmitted.collectAsState()

    // Variable para indicar si el formulario fue enviado

    // Cargar las variedades de café
    val context = LocalContext.current
    LaunchedEffect(Unit) {}

    /*// Inicializar el ViewModel con los valores pasados si están presentes
    LaunchedEffect(plotName, selectedVariety) {
        if (plotName.isNotEmpty()) {
            viewModel.onPlotNameChange(plotName)
        }
        if (selectedVariety.isNotEmpty()) {
            viewModel.onVarietyChange(selectedVariety)
        }
    }*/

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(horizontal = 20.dp, vertical = 30.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                // Botón de cerrar o volver (BackButton)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    BackButton(
                        navController = navController,
                        onClick = { navController.navigate("FarmInformationView/${farmId}") },
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Añadir Labor",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF49602D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Lote: $plotName",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFF94A84B)
                    ),
                    color = Color(0xFF94A84B))

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Tipo Labor Cultural",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFF3F3D3D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(2.dp))

                TypeCulturalWorkDropdown(
                    selectedCulturalWork = "",
                    onTypeCulturalWorkChange = { },
                    cultural_work = typeCulturalWork,  // Lista de roles obtenida del ViewModel
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Fecha",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFF3F3D3D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))

                DatePickerComposable(
                    label = "Fecha de floración",
                    selectedDate = "",
                    onDateSelected = { },
                    errorMessage = if (isFormSubmitted && flowering_date.isBlank()) "La fecha de floración no puede estar vacía." else null
                )


                Spacer(modifier = Modifier.height(16.dp))

                // Botón Siguiente
                ReusableButton(
                    text = "Siguiente",
                    onClick = {},
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp)
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Green
                )
            }
        }
    }
}





// Mueve la función Preview fuera de la función CreatePlotView
@Preview(showBackground = true)
@Composable
fun AddCulturalWorkView1Preview() {
    val navController = rememberNavController() // Usar rememberNavController para la vista previa

    CoffeTechTheme {
        AddCulturalWorkView1(
            navController = navController,
            farmId= 1
        )
    }
}