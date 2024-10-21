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
import androidx.compose.ui.text.font.FontWeight
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
import com.example.coffetech.viewmodel.flowering.EditFloweringViewModel


@Composable
fun EditFloweringView(
    navController: NavController,
    plotId: Int,
    floweringTypeName: String,
    floweringDate: String,
    harvestDate: String,

    viewModel: EditFloweringViewModel = viewModel()
) {

    val context = LocalContext.current
    val showDeleteConfirmation = remember { mutableStateOf(false) }
    val currentFloweringName by viewModel.selectedFloweringName.collectAsState()
    val floweringName by viewModel.floweringName.collectAsState()
    val currentFlowering_date by viewModel.flowering_date.collectAsState()
    val currentHarvest_date by viewModel.harvest_date.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val hasChanges by viewModel.hasChanges.collectAsState()
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
                    selectedFloweringName = currentFloweringName,
                    onFloweringNameChange = viewModel::onFloweringNameChange,
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
                    value = currentFlowering_date,
                    onValueChange = { viewModel.onFloweringDateChange(it) },
                    placeholder = "Fecha de floracion",
                    charLimit = 50,
                    isValid = currentFlowering_date.isNotEmpty() || !isFormSubmitted.value,
                    modifier = Modifier.fillMaxWidth(),
                    errorMessage = if (currentFlowering_date.isEmpty() && isFormSubmitted.value) "La fecha de floracion no puede estar vacía" else ""
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
                    value = currentHarvest_date,
                    onValueChange = { viewModel.onFloweringDateChange(it) },
                    placeholder = "Fecha de cosecha",
                    charLimit = 50,
                    isValid = currentHarvest_date.isNotEmpty() || !isFormSubmitted.value,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar cambios
                ReusableButton(
                    text = if (isLoading) "Guardando..." else "Guardar",
                    onClick = {
                        viewModel.editFlowering(
                            context = context,
                            navController = navController
                        )
                    },
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp) // Ajuste de tamaño del botón
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Green,
                    enabled = hasChanges && !isLoading
                )

                // Botón para eliminar el colaborador
                Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre botones

                ReusableButton(
                    text = if (isLoading) "Cargando..." else "Eliminar",
                    onClick = {showDeleteConfirmation.value = true},
                    enabled = !isLoading,
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp)
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Red,
                )

                //Confirmación para eliminar colaborador
                if (showDeleteConfirmation.value) {
                    Box(
                        modifier = Modifier
                    ) {
                        AlertDialog(
                            containerColor = Color.White,
                            modifier = Modifier
                                .background(Color.Transparent),
                            onDismissRequest = { showDeleteConfirmation.value = false },
                            title = {
                                Text(
                                    text = "¡Esta acción es irreversible!",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                )
                            },
                            text = {
                                // Contenedor para el contenido del diálogo
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp), // Espacio alrededor del contenido
                                    horizontalAlignment = Alignment.CenterHorizontally // Centrar el contenido
                                ) {
                                    // Descripción centrada
                                    Text(
                                        text = "Esta floracion se eliminará permanentemente. ¿Deseas continuar?",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            },


                            confirmButton = {
                                // Botón para eliminar centrado
                                ReusableButton(
                                    text = if (isLoading) "Eliminando..." else "Eliminar",
                                    onClick = {
                                        viewModel.deleteFlowering(
                                            context = context,
                                            navController = navController
                                        )
                                        showDeleteConfirmation.value = false
                                    },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(0.7f),
                                    buttonType = ButtonType.Red,
                                )
                            },
                            dismissButton = {
                                // Botón cancelar
                                ReusableButton(
                                    text = "Cancelar",
                                    onClick = { showDeleteConfirmation.value = false },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(0.7f),
                                    buttonType = ButtonType.Green,
                                )
                            },

                            shape = RoundedCornerShape(16.dp) // Esquinas redondeadas del diálogo
                        )
                    }
                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditFloweringViewPreview() {
    val mockNavController = rememberNavController() // MockNavController
    CoffeTechTheme {
        EditFloweringView(
            navController = mockNavController,
            plotId = 1, // Ejemplo de ID de la finca
            floweringTypeName= "Principal",
            floweringDate= "15-08-2024",
            harvestDate= "15-08-2025",
        )
    }
}