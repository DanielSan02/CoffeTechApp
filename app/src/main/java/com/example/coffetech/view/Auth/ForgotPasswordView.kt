// ForgotPasswordView.kt (View)

package com.example.coffetech.view.Auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.LargeText
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.ForgotPasswordViewModel

@Composable
fun ForgotPasswordView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    val email by viewModel.email
    val isEmailValid by viewModel.isEmailValid
    val errorMessage by viewModel.errorMessage
    val context = LocalContext.current // Obtener el contexto aquí

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LargeText(
                text = "Restablecer contraseña",
                modifier = Modifier.padding(top = 30.dp, bottom = 30.dp)
            )

            ReusableDescriptionText(text = "Te enviaremos un email con las instrucciones para restablecer tu contraseña")

            ReusableTextField(
                value = email,
                onValueChange = {
                    viewModel.onEmailChange(it)
                },
                placeholder = "Correo Electrónico",
                isValid = isEmailValid,
                errorMessage = if (isEmailValid) "" else "Correo electrónico no válido"
            )

            ForgotButton(
                isEmailValid = isEmailValid,
                onSendRequest = { viewModel.sendForgotPasswordRequest(navController, context) } // Pasar el contexto aquí
            )

            ForgotBack(navController = navController)
        }
    }
}

@Composable
fun ForgotButton(
    isEmailValid: Boolean,
    onSendRequest: () -> Unit
) {
    Button(
        onClick = { onSendRequest() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D)),
        modifier = Modifier.padding(bottom = 16.dp),
        enabled = isEmailValid // Desactivar botón si el correo no es válido
    ) {
        Text("Enviar correo")
    }
}

@Composable
fun ForgotBack(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.LoginView)
        },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("Volver", color = Color(0xFF49602D))
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    CoffeTechTheme {
        ForgotPasswordView(navController = NavController(LocalContext.current))
    }
}
