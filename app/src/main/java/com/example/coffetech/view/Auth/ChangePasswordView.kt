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
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.ReusableCancelButton
import com.example.coffetech.common.ReusableFieldLabel
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.TopBarWithBackArrow
import com.example.coffetech.viewmodel.Auth.ChangePasswordViewModel

/**
 * Composable function that renders a view for changing the user's password.
 *
 * @param modifier A [Modifier] for adjusting the layout or appearance of the view.
 * @param navController The [NavController] used for navigation between screens.
 * @param viewModel The [ChangePasswordViewModel] that manages the state of the password change process.
 */
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
    val isLoading by viewModel.isLoading

    LaunchedEffect(isPasswordChanged) {
        if (isPasswordChanged) {
            navController.popBackStack() // Navegar a la pantalla anterior
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopBarWithBackArrow(
            onBackClick = { navController.navigate(Routes.ProfileView)},
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
                margin = 0.dp,
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

            SavePasswordButton(
                isLoading = isLoading,
                onSaveClick = {
                    if (viewModel.validatePasswordRequirements()) {
                        viewModel.changePassword(context)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ReusableCancelButton(
                navController = navController,
                destination = Routes.ProfileView // Aquí puedes definir a qué ruta navegar cuando presionas "Cancelar"
            )
        }
    }
}

/**
 * Composable function that renders a save button for changing the password.
 *
 * @param isLoading A boolean indicating whether the password is currently being saved. Disables the button if true.
 * @param onSaveClick A lambda function that triggers when the button is clicked and saving is not in progress.
 */
@Composable
fun SavePasswordButton(
    isLoading: Boolean,
    onSaveClick: () -> Unit
) {
    Button(
        onClick = { if (!isLoading) onSaveClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF49602D),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(bottom = 16.dp),
        enabled = !isLoading // Deshabilita el botón si está en estado de carga
    ) {
        if (isLoading) {
            Text("Guardando...") // Texto cuando está guardando
        } else {
            Text("Guardar") // Texto normal
        }
    }
}

/**
 * A preview composable function to simulate and display the [ChangePasswordView] in a preview window.
 */
@Preview(showBackground = true)
@Composable
fun ChangePasswordViewPreview() {
    ChangePasswordView(
        navController = rememberNavController() // Simula un NavController para la vista previa
    )
}
