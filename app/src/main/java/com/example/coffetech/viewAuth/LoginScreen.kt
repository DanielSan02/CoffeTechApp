package com.example.coffetech.viewAuth

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.LargeText
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.data.LoginRequest
import com.example.coffetech.data.LoginResponse
import com.example.coffetech.data.RetrofitInstance
import com.example.coffetech.ui.theme.CoffeTechTheme
import retrofit2.Call
import retrofit2.Callback


@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Box( modifier = modifier
        .fillMaxSize()
        .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 25.dp),
            // Hacer la columna scrolleable
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoImage()
            LargeText(text = "!Bienvenido!", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            ReusableDescriptionText(text = "Por favor inicia sesión para continuar")

            ReusableTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Correo Electrónico"
            )

            ReusableTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Contraseña",
                isPassword = true
            )
            // Mostrar mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            ForgotPasswordButton(navController = navController)
            LoginButton(
                email = email,
                password = password,
                onLoginSuccess = {
                    // Navegar a la pantalla de inicio o pantalla principal después del inicio de sesión exitoso
                    //navController.navigate(Routes.HomeScreen)
                },
                onLoginError = { errorMessage = it } // Mostrar mensaje de error en caso de fallo
            )

            LoginToRegisterButton(navController = navController)
        }
    }
}


@Composable
fun ForgotPasswordButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.ForgotScreen)
        },
        modifier = Modifier
            .padding(bottom = 16.dp),
    ) {
        Text("Olvide la contraseña",
            color = Color(0xFF49602D))

    }
}


@Composable
fun LoginButton(
    email: String,
    password: String,
    onLoginSuccess: () -> Unit, // Callback para manejar el éxito del inicio de sesión
    onLoginError: (String) -> Unit // Callback para manejar errores
) {
    val context = LocalContext.current

    Button(
        onClick = {
            // Validar que los campos no estén vacíos
            if (email.isBlank() || password.isBlank()) {
                onLoginError("El correo y la contraseña son obligatorios")
                return@Button
            }

            // Crear la solicitud de inicio de sesión
            val loginRequest = LoginRequest(email = email, password = password)

            // Enviar la solicitud al servidor
            RetrofitInstance.api.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: retrofit2.Response<LoginResponse>
                ) { // Elimina <LoginResponse>
                    if (response.isSuccessful) {
                        // Manejar respuesta exitosa
                        Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                        onLoginSuccess()
                    } else {
                        // Manejar error en la respuesta
                        onLoginError("Error al iniciar sesión: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Manejar fallo en la solicitud
                    onLoginError("Error de red: ${t.localizedMessage}")
                }
            })
        },
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
            navController.navigate(Routes.RegisterScreen)
        },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("¿No tienes cuenta? Registrarse",
            color = Color(0xFF49602D))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CoffeTechTheme {
        LoginScreen(navController = NavController(LocalContext.current))
        }
}
