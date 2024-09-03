package com.example.coffetech.viewAuth

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.LargeText
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.data.ForgotPasswordRequest
import com.example.coffetech.data.ForgotPasswordResponse
import com.example.coffetech.data.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
@Composable
fun ForgotPassword(modifier: Modifier = Modifier, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) } // Estado para rastrear la validez del correo
    var errorMessage by remember { mutableStateOf("") }


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
                    email = it
                    isEmailValid = isValidEmail(it) // Validar correo cada vez que cambie
                },
                placeholder = "Correo Electrónico", // Cambiado a "Correo Electrónico" para la validación
                isValid = isEmailValid,
                errorMessage = if (isEmailValid) "" else "Correo electrónico no válido"
            )

            ForgotButton(
                isEmailValid = isEmailValid,
                email = email,
                navController = navController,
                onValidationError = { errorMessage = it }
            )
            ForgotBack(navController = navController)
        }
    }
}


@Composable
fun ForgotTitle() {
    Text(
        text = "Restablecer contraseña",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 40.sp,
            fontWeight = FontWeight.W800),
            textAlign = TextAlign.Center,
        color = Color(0xFF31373E),
        modifier = Modifier.padding(top = 10.dp, bottom = 40.dp)
    )
}

@Composable
fun ForgotText() {
    Text(
        text = "Te enviaremos un email con las instrucciones para restablecer tu contraseña",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 17.sp,
            fontWeight = FontWeight.W300),
            textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 50.dp, top = 5.dp, start= 50.dp, end = 40.dp)
    )
}

@Composable
fun ForgotEmailField(email: String, onEmailChange: (String) -> Unit, isEmailValid: Boolean) {
    Column {
        TextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text("Correo Electrónico") },
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent, // Para quitar la línea del textfield
                unfocusedIndicatorColor = Color.Transparent // Para quitar la línea del textfield
            ),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .border(2.dp, if (isEmailValid) Color.Gray else Color.Red, RoundedCornerShape(4.dp)) // Cambiar el color del borde si no es válido
        )
        if (!isEmailValid) {
            Text(
                text = "Correo electrónico no válido",
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp)
            )
        }
    }
}

@Composable
fun ForgotButton(
    isEmailValid: Boolean,
    email: String,
    navController: NavController,
    onValidationError: (String) -> Unit
){
    val context = LocalContext.current

    Button(
        onClick = {
            if (isEmailValid) {
                // Crear la solicitud de restablecimiento de contraseña
                val forgotPasswordRequest = ForgotPasswordRequest(email)

                // Enviar la solicitud al servidor
                RetrofitInstance.api.forgotPassword(forgotPasswordRequest).enqueue(object : Callback<ForgotPasswordResponse> {
                    override fun onResponse(call: Call<ForgotPasswordResponse>, response: Response<ForgotPasswordResponse>) {
                        if (response.isSuccessful) {
                            // Manejar respuesta exitosa
                            Toast.makeText(context, "Correo enviado con éxito", Toast.LENGTH_LONG).show()
                            navController.navigate(Routes.AlertSend)
                        } else {
                            // Manejar error en la respuesta
                            onValidationError("Error al enviar correo: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                        // Manejar fallo en la solicitud
                        onValidationError("Error de red: ${t.localizedMessage}")
                    }
                })
            } else {
                onValidationError("Correo electrónico no válido")
            }
        },
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
        onClick = { navController.navigate(Routes.LoginScreen) },
        modifier = Modifier
            .padding(bottom = 5.dp),
    ) {
        Text("Volver",
            color = Color(0xFF49602D))

    }
}
fun isValidEmail(email: String): Boolean {
    return emailRegex.matches(email)
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    CoffeTechTheme {
        ForgotPassword(navController = NavController(LocalContext.current))
    }
}