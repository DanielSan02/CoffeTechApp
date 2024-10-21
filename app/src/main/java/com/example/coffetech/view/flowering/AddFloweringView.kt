package com.example.coffetech.view.flowering

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.coffetech.common.FloweringNameDropdown
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.RoleAddDropdown
import com.example.coffetech.common.VarietyCoffeeDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.Collaborator.AddCollaboratorView
import com.example.coffetech.viewmodel.flowering.AddFloweringViewModel


@Composable
fun AddFloweringView(
    navController: NavController,
    plotId: Int,
    FloweringTypeName: String = "",
    FloweringDate: String = "",
    HarvestDate: String = "",

    viewModel: AddFloweringViewModel = viewModel()
) {
    val context = LocalContext.current
    val selectedFloweringName by viewModel.selectedFloweringName.collectAsState()
    val floweringName by viewModel.floweringName.collectAsState()
    val flowering_date by viewModel.flowering_date.collectAsState()
    val harvest_date by viewModel.flowering_date.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Variable para indicar si el formulario fue enviado
    val isFormSubmitted = remember { mutableStateOf(false) }


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
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
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

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Agregar Floración",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF49602D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(45.dp))

                FloweringNameDropdown(
                    selectedFloweringName = selectedFloweringName,
                    onFloweringNameChange = { viewModel.onFloweringNameChange(it) },
                    flowerings = floweringName,  // Lista de roles obtenida del ViewModel
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    modifier = Modifier.fillMaxWidth()
                )

                // Mostrar mensaje de error si lo hay
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Text(
                    text = "Fecha de Floración",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFF3F3D3D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Campo de texto para el nombre del lote
                ReusableTextField(
                    value = flowering_date,
                    onValueChange = { viewModel.onFloweringDateChange(it) },
                    placeholder = "Fecha de floracion",
                    charLimit = 50,
                    isValid = flowering_date.isNotEmpty() || !isFormSubmitted.value,
                    modifier = Modifier.fillMaxWidth(),
                    errorMessage = if (flowering_date.isEmpty() && isFormSubmitted.value) "La fecha de floraciona no puede estar vacía" else ""
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Fecha de Cosecha (Opcional)",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFF3F3D3D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(2.dp))

                ReusableTextField(
                    value = harvest_date,
                    onValueChange = { viewModel.onHarvestDateChange(it) },
                    placeholder = "Fecha de cosecha",
                    charLimit = 50,
                    isValid = harvest_date.isNotEmpty() || !isFormSubmitted.value,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                ReusableButton(
                    text = if (isLoading) "Creando..." else "Crear",
                    onClick = {
                        if (viewModel.validateInputs()) {
                            viewModel.onCreate(navController, context, plotId)  // Enviar farmId
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Green,
                    enabled = !isLoading
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddFloweringViewPreview() {
    val mockNavController = rememberNavController() // MockNavController
    CoffeTechTheme {
        AddFloweringView(
            navController = mockNavController,
            plotId = 1, // Ejemplo de ID de la finca
        )
    }
}
