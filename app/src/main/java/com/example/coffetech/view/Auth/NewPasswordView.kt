// NewPasswordView.kt

package com.example.coffetech.view.Auth

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.NewPasswordViewModel

@Composable
fun NewPasswordView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewPasswordViewModel = viewModel(),
    token: String // Asegúrate de recibir el token aquí
) {

    val password by viewModel.password
    val confirmPassword by viewModel.confirmPassword
    val errorMessage by viewModel.errorMessage
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Verifica si el token es válido y haz un registro del mismo
    if (token.isBlank()) {
        Log.e("NewPasswordView", "Token es nulo o vacío.")
        Toast.makeText(context, "Error: Token inválido", Toast.LENGTH_SHORT).show()
        return // Detén la composición si el token es inválido
    } else {
        Log.d("NewPasswordView", "Token recibido correctamente: $token")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(Color(0xFFF2F2F2)),
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
            Spacer(modifier = Modifier.height(40.dp))

            ReusableDescriptionText(text = "Ingrese su nueva contraseña", fontSize = 25)
            Spacer(modifier = Modifier.height(16.dp))

            ReusableTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = "Nueva Contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            ReusableTextField(
                value = confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                placeholder = "Confirmar Contraseña",
                isPassword = true
            )

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            ResetPasswordButton(
                onResetClick = { viewModel.resetPassword(navController, context, token) }
            )

            CancelButton(navController = navController)
        }
    }
}


@Composable
fun ResetPasswordButton(
    onResetClick: () -> Unit
) {
    Button(
        onClick = { onResetClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF49602D),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("Restablecer")
    }
}

@Composable
fun CancelButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.LoginView)
        },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("Cancelar", color = Color(0xFF49602D))
    }
}

@Preview(showBackground = true)
@Composable
fun NewPasswordPreview() {
    CoffeTechTheme {
        // Para la vista previa, se proporciona un token de ejemplo
        NewPasswordView(navController = NavController(LocalContext.current), token = "sampleToken")
    }
}
