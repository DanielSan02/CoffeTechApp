// ConfirmTokenForgotPasswordView.kt (View)

package com.example.coffetech.view.Auth

import androidx.activity.compose.BackHandler
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
import com.example.coffetech.common.ReusableCancelButton
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.ConfirmTokenForgotPasswordViewModel

@Composable
fun ConfirmTokenForgotPasswordView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ConfirmTokenForgotPasswordViewModel = viewModel()
) {


    val token by viewModel.token
    val errorMessage by viewModel.errorMessage
    val context = LocalContext.current // Obtener el contexto aquí
    val isLoading by viewModel.isLoading

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
            LargeText(text = "Restablecer Contraseña", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            ReusableDescriptionText(text = "Por favor, introduce el código para restablecer tu contraseña")

            ReusableTextField(
                value = token,
                onValueChange = {
                    viewModel.onTokenChange(it)
                },
                placeholder = "Token"
            )

            ConfirmButton(
                isLoading = isLoading,
                token = token,
                onConfirmClick = { viewModel.confirmToken(navController, context) }
            )

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            ReusableCancelButton(
                navController = navController,
                destination = Routes.LoginView // Aquí puedes definir a qué ruta navegar cuando presionas "Cancelar"
            )
        }
    }
}

@Composable
fun ConfirmButton(
    isLoading: Boolean,
    token: String,
    onConfirmClick: () -> Unit
) {
    Button(
        onClick = {if (!isLoading) onConfirmClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF49602D),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp),
        enabled = !isLoading // Deshabilita el botón si está cargando

    ) {
        if (isLoading) {
            Text("Confirmando...") // Texto mientras está cargando
        } else {
            Text("Confirmar código") // Texto normal
        }
    }


}


@Preview(showBackground = true)
@Composable
fun ConfirmTokenForgotPasswordPreview() {
    CoffeTechTheme {
        ConfirmTokenForgotPasswordView(navController = NavController(LocalContext.current))
    }
}
