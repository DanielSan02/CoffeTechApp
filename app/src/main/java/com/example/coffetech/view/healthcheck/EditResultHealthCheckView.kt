package com.example.coffetech.view.healthcheck

import com.example.coffetech.common.ReusableAlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.coffetech.common.DetectionResultInfoCard
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Collaborator.EditCollaboratorViewModel
import com.example.coffetech.viewmodel.healthcheck.EditResultHealthCheckViewModel


@Composable
fun EditResultHealthCheckView(
    navController: NavController,
    checkingId: Int,
    farmId: Int,
    farmName: String,
    plotName: String,

    viewModel: EditResultHealthCheckViewModel = viewModel()
) {
    val context = LocalContext.current
    val showDeleteConfirmation = remember { mutableStateOf(false) }



    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val scrollState = rememberScrollState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Fondo oscuro
            .padding(16.dp),
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
                    .padding(10.dp) // Añadir padding interno
                    .verticalScroll(scrollState)


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

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Detección",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy( // Usamos el estilo predefinido y sobreescribimos algunas propiedades
                        // Sobrescribir el tamaño de la fuente
                        color = Color(0xFF49602D)      // Sobrescribir el color
                    ),
                    modifier = Modifier.fillMaxWidth()  // Ocupa todo el ancho disponible
                )

                Spacer(modifier = Modifier.height(45.dp))
/*
                DetectionResultInfoCard(
                    detectionId = checkingId,
                    nameFarm = farmName,
                    namePlot = plotName,
                    description = "Descripción de la detección aquí.", // Aquí puedes ajustar la descripción
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                )
*/

                Spacer(modifier = Modifier.height(2.dp))



                // Mostrar mensaje de error si lo hay
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))


                ReusableButton(
                    text = if (isLoading) "Regresando..." else "Volver",
                    onClick = {},
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp)
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Green,
                    enabled = !isLoading
                )


                Spacer(modifier = Modifier.height(16.dp))

                ReusableButton(
                    text = if (isLoading) "Cargando..." else "Eliminar",
                    onClick = {showDeleteConfirmation.value = true},
                    enabled = !isLoading,
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp)
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Red,
                )

                val image = painterResource(id = R.drawable.delete_confirmation_icon)
                //Confirmación para eliminar deteccion
                if (showDeleteConfirmation.value) {
                    ReusableAlertDialog(
                        title = "¡ESTA ACCIÓN\nES IRREVERSIBLE!",
                        description = "Todos tus datos relacionados a esta detección se perderán. ¿Deseas continuar?",
                        confirmButtonText = "Descartar detección",
                        cancelButtonText = "Cancelar",
                        isLoading = isLoading,
                        onConfirmClick = {},
                        onCancelClick = { showDeleteConfirmation.value = false },
                        onDismissRequest = { showDeleteConfirmation.value = false },
                        image = image
                    )
                }



            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditResultHealthCheckViewPreview() {
    val mockNavController = rememberNavController() // MockNavController
    CoffeTechTheme {
        EditResultHealthCheckView(
            navController = mockNavController,
            checkingId = 1, // Ejemplo de ID de la finca
            farmId = 1,
            farmName = "Finca 2",
            plotName = "Lote 1"
        )
    }
}
