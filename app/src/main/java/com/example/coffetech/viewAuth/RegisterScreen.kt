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
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.data.RegisterRequest
import com.example.coffetech.data.RegisterResponse
import com.example.coffetech.data.RetrofitInstance
import com.example.coffetech.ui.theme.CoffeTechTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun RegisterScreen(modifier: Modifier = Modifier, navController: NavController){

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Box (
        modifier = modifier
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
            LargeText(text = "Crea tu Cuenta", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            ReusableTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Nombre"
            )

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

            ReusableTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirmar Contraseña",
                isPassword = true
            )


            // Mostrar mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            RegisterButton(
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                navController = navController,
                onValidationError = { errorMessage = it }
            )



            ToLoginButton(navController = navController)
        }
    }


}


@Composable
fun RegisterButton(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    navController: NavController,
    onValidationError: (String) -> Unit
) {
    val context = LocalContext.current

    Button(
        onClick = {
            // Validar que todos los campos estén completos
            if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                onValidationError("Todos los campos son obligatorios")
                return@Button
            }

            val (isValidPassword, passwordMessage) = validatePassword(password, confirmPassword)
            val isValidEmail = validateEmail(email)

            if (!isValidEmail) {
                onValidationError("Correo electrónico no válido")
            } else if (!isValidPassword) {
                onValidationError(passwordMessage)
            } else {
                onValidationError("") // Limpiar el mensaje de error

                // Crear la solicitud de registro
                val registerRequest = RegisterRequest(name, email, password, confirmPassword)

                // Enviar la solicitud al servidor
                RetrofitInstance.api.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            // Manejar respuesta exitosa
                            Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_LONG).show()
                            navController.navigate(Routes.VerifyAccount)
                        } else {
                            // Manejar error en la respuesta
                            onValidationError("Error al registrar usuario: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        // Manejar fallo en la solicitud
                        onValidationError("Error de red: ${t.localizedMessage}")
                    }
                })
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D)),
        modifier = Modifier.padding(bottom = 5.dp, top = 18.dp)
    ) {
        Text("Registrarse")
    }
}



@Composable
fun ToLoginButton(navController: NavController) {
    TextButton(
        onClick = { navController.navigate(Routes.LoginScreen) },

        ) {
        Text("¿Ya tienes cuenta? Inicia sesión",
            color = Color(0xFF49602D))

    }
}
fun validatePassword(password: String, confirmPassword: String): Pair<Boolean, String> {
    val specialCharacterPattern = Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")
    val uppercasePattern = Regex(".*[A-Z].*")

    return when {
        password != confirmPassword -> Pair(false, "Las contraseñas no coinciden")
        password.length < 8 -> Pair(false, "La contraseña debe tener al menos 8 caracteres")
        !specialCharacterPattern.containsMatchIn(password) -> Pair(false, "La contraseña debe contener al menos un carácter especial")
        !uppercasePattern.containsMatchIn(password) -> Pair(false, "La contraseña debe contener al menos una letra mayúscula")
        else -> Pair(true, "Contraseña válida")
    }
}

fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    CoffeTechTheme {
        RegisterScreen(navController = NavController(LocalContext.current))
    }
}