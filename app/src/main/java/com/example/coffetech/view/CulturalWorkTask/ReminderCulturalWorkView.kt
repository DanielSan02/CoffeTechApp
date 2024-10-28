package com.example.coffetech.view.CulturalWorkTask

import androidx.compose.ui.text.style.TextAlign
import com.example.coffetech.common.ButtonType
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.CulturalWorkTask.ReminderViewModel

@Composable
fun ReminderCulturalWorkView(
    navController: NavController,
    viewModel: ReminderViewModel = viewModel(),
    farmId: Int,
    plotName: String = ""
) {
    val isReminderForUser by viewModel.isReminderForUser.collectAsState()
    val isReminderForCollaborator by viewModel.isReminderForCollaborator.collectAsState()

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
                // Botón de cerrar o volver
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    BackButton(
                        navController = navController,
                        onClick = { navController.navigate("PreviousView/${farmId}") },
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Recordatorios",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF49602D)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Seleccione los recordatorios que desea establecer y de click en guardar",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF3F3D3D)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Checkbox para enviar recordatorio al usuario
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isReminderForUser,
                        onCheckedChange = { viewModel.setReminderForUser(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF5D8032),
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = "Enviarme un recordatorio",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF3F3D3D)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isReminderForCollaborator,
                        onCheckedChange = { viewModel.setReminderForCollaborator(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF5D8032),
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                    )
                    )
                    Text(
                        text = "Enviar recordatorio al colaborador asignado",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF3F3D3D)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Botón de guardar
                ReusableButton(
                    text = "Guardar",
                    onClick = {
                        viewModel.saveReminders()
                        navController.navigate("PreviousView/${farmId}") // Navegar a la vista anterior o de confirmación
                    },
                    modifier = Modifier
                        .size(width = 160.dp, height = 48.dp)
                        .align(Alignment.CenterHorizontally),
                    buttonType = ButtonType.Green
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReminderCulturalWorkViewPreview() {
    val navController = rememberNavController() // Usar rememberNavController para la vista previa

    CoffeTechTheme {
        ReminderCulturalWorkView(
            navController = navController,
            farmId= 1
        )
    }
}
