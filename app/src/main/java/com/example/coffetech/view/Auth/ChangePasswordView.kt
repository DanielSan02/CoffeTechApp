package com.example.coffetech.view.Auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.common.ReusableFieldLabel
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.TopBarWithBackArrow
import com.example.coffetech.viewmodel.Auth.ChangePasswordViewModel

@Composable
fun ChangePasswordView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ChangePasswordViewModel = viewModel()
) {
    val currentPassword by viewModel.currentPassword
    val newPassword by viewModel.newPassword
    val confirmPassword by viewModel.confirmPassword
    val errorMessage by viewModel.errorMessage
    val isPasswordChanged by viewModel.isPasswordChanged
    val context = LocalContext.current // Obtener el contexto aquí

    LaunchedEffect(isPasswordChanged) {
        if (isPasswordChanged) {
            navController.popBackStack() // Navegar a la pantalla anterior
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopBarWithBackArrow(
            onBackClick = { navController.popBackStack() },
            title = "Actualizar contraseña"
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            ReusableFieldLabel(text = "Contraseña actual",
                modifier = Modifier.padding(start = 25.dp))

            ReusableTextField(
                value = currentPassword,
                onValueChange = { viewModel.onCurrentPasswordChange(it) },
                placeholder = "Contraseña actual",
                margin=0.dp,
                isPassword = true,

            )

            Spacer(modifier = Modifier.height(16.dp))

            ReusableFieldLabel(text = "Nueva contraseña",
                modifier = Modifier.padding(start = 25.dp))

            ReusableTextField(
                value = newPassword,
                onValueChange = { viewModel.onNewPasswordChange(it) },
                placeholder = "Nueva contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            ReusableFieldLabel(text = "Confirme nueva contraseña",
                modifier = Modifier.padding(start = 25.dp))

            ReusableTextField(
                value = confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                placeholder = "Confirme nueva contraseña",
                isPassword = true
            )

            // Mostrar el mensaje de error si las contraseñas no cumplen con los requisitos de seguridad
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Validar si las contraseñas son seguras antes de intentar cambiarlas
                    if (viewModel.validatePasswordRequirements()) {
                        viewModel.changePassword(context)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF49602D),
                    contentColor = Color.White)
            ) {
                Text("Guardar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Cancelar", color = Color(0xFF49602D))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChangePasswordViewPreview() {
    ChangePasswordView(
        navController = rememberNavController() // Simula un NavController para la vista previa
    )
}
