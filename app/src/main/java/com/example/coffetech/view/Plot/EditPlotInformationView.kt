package com.example.coffetech.view.Plot

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.R
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.VarietyCoffeeDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Plot.EditPlotInformationViewModel

@Composable
fun EditPlotInformationView(
    navController: NavController,
    plotId: Int,
    plotName: String,
    selectedVariety: String,
    viewModel: EditPlotInformationViewModel = viewModel()
) {

    val TAG = "EditPlotInformationView"
    Log.d(TAG, "Composable EditPlotInformationView iniciado con plotId: $plotId, plotName: $plotName, selectedVariety: $selectedVariety")

    val context = LocalContext.current

    // Obtener los estados del ViewModel
    val currentPlotName by viewModel.plotName.collectAsState()
    val currentSelectedVariety by viewModel.selectedVariety.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val plotCoffeeVariety by viewModel.plotCoffeeVariety.collectAsState()
    val hasChanges by viewModel.hasChanges.collectAsState()

    // Inicializar el ViewModel con los valores recibidos
    LaunchedEffect(Unit) {
        viewModel.initialize(plotId, plotName, selectedVariety, context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Fondo oscuro
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
                    text = "Editar Lote",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF49602D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(45.dp))

                Text(
                    text = "Nombre",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFF3F3D3D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Campo de texto para el nombre del lote
                ReusableTextField(
                    value = currentPlotName,
                    onValueChange = { viewModel.onPlotNameChange(it) },
                    placeholder = "Nombre del lote",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Variedad de Café",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color(0xFF3F3D3D)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Dropdown para seleccionar variedad de café
                VarietyCoffeeDropdown(
                    selectedVariety = currentSelectedVariety,
                    onVarietyChange = { viewModel.onVarietyChange(it) },
                    varieties = plotCoffeeVariety,
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Mostrar mensaje de error si lo hay
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar
                ReusableButton(
                    text = if (isLoading) "Guardando..." else "Guardar",
                    onClick = {
                        viewModel.saveChanges(
                            plotId = plotId,
                            navController = navController,
                            onSuccess = {
                                Toast.makeText(context, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp)
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
fun EditPlotInformationViewPreview() {
    val navController = rememberNavController()
    CoffeTechTheme {
        EditPlotInformationView(
            navController = navController,
            plotId = 1,
            plotName = "Lote 1",
            selectedVariety = "Caturra"
        )
    }
}
