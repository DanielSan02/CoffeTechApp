package com.example.coffetech.view.Auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // Importación correcta para obtener ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.common.LargeText
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.viewmodel.Auth.LoginViewModel

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = viewModel() // Usando la función de Compose para obtener el ViewModel
) {
    val email by viewModel.email
    val password by viewModel.password
    val errorMessage by viewModel.errorMessage
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoImage()
            LargeText(text = "!Bienvenido!", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            ReusableDescriptionText(text = "Por favor inicia sesión para continuar")

            ReusableTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = "Correo Electrónico"
            )

            ReusableTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = "Contraseña",
                isPassword = true
            )

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            ForgotPasswordButton(navController = navController)
            LoginButton(
                onLoginClick = { viewModel.loginUser(navController, context) }, // Usa el ViewModel para manejar el click
                context = context
            )

            LoginToRegisterButton(navController = navController)
        }
    }
}

@Composable
fun ForgotPasswordButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.ForgotPasswordView)
        },
        modifier = Modifier.padding(bottom = 16.dp),
    ) {
        Text("Olvide la contraseña", color = Color(0xFF49602D))
    }
}

@Composable
fun LoginButton(
    onLoginClick: () -> Unit,
    context: android.content.Context
) {
    Button(
        onClick = { onLoginClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D)),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("Iniciar sesión")
    }
}

@Composable
fun LoginToRegisterButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.RegisterView)
        },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("¿No tienes cuenta? Registrarse", color = Color(0xFF49602D))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CoffeTechTheme {
        LoginView(navController = NavController(LocalContext.current))
    }
}
