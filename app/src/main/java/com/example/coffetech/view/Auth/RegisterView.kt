// RegisterScreen.kt (View)

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.LargeText
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.RegisterViewModel

@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RegisterViewModel = viewModel() // Usa la función de Compose para obtener el ViewModel
) {
    val name by viewModel.name
    val email by viewModel.email
    val password by viewModel.password
    val confirmPassword by viewModel.confirmPassword
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
            LargeText(text = "Crea tu Cuenta", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            ReusableTextField(
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                placeholder = "Nombre"
            )

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

            ReusableTextField(
                value = confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                placeholder = "Confirmar Contraseña",
                isPassword = true
            )

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            RegisterButton(
                onRegisterClick = { viewModel.registerUser(navController, context) }
            )

            ToLoginButton(navController = navController)
        }
    }
}

@Composable
fun RegisterButton(onRegisterClick: () -> Unit) {
    Button(
        onClick = { onRegisterClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF49602D),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(bottom = 5.dp, top = 18.dp)
    ) {
        Text("Registrarse")
    }
}

@Composable
fun ToLoginButton(navController: NavController) {
    TextButton(
        onClick = { navController.navigate(Routes.LoginView) },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF49602D))
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    CoffeTechTheme {
        RegisterView(navController = NavController(LocalContext.current))
    }
}
