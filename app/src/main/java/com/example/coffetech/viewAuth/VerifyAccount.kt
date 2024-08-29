package com.example.coffetech.viewAuth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.data.VerifyRequest
import com.example.coffetech.data.VerifyResponse
import com.example.coffetech.data.RetrofitInstance
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.R
import com.example.coffetech.common.LargeText
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableTextField
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun VerifyAccount(modifier: Modifier = Modifier, navController: NavController) {
    var token by remember { mutableStateOf("") }
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
            LargeText(text = "Verifica tu cuenta", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            ReusableDescriptionText(text = "Por favor, " +
                    "introduce el token para verificar tu email\"")

            ReusableTextField(
                value = token,
                onValueChange = { token = it },
                placeholder = "Token"
            )

            VerifyButton(
                token = token,
                navController= navController,
                onVerifySuccess = {
                    // Navegar a la pantalla de éxito después de la verificación
                    navController.navigate(Routes.LoginScreen)
                },
                onVerifyError = { errorMessage = it }
            )
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}


@Composable
fun VerifyButton(
    token: String,
    navController: NavController,
    onVerifySuccess: () -> Unit,
    onVerifyError: (String) -> Unit
) {
    val context = LocalContext.current

    Button(
        onClick = {
            if (token.isBlank()) {
                onVerifyError("El token es obligatorio")
                return@Button
            }

            val verifyRequest = VerifyRequest(token = token)

            RetrofitInstance.api.verifyUser(verifyRequest).enqueue(object : Callback<VerifyResponse> {
                override fun onResponse(
                    call: Call<VerifyResponse>,
                    response: Response<VerifyResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Verificación de email exitosa", Toast.LENGTH_LONG).show()
                        onVerifySuccess()
                        navController.navigate(Routes.LoginScreen)
                    } else {
                        onVerifyError("Error al verificar email: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                    onVerifyError("Error de red: ${t.localizedMessage}")
                }
            })
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D)),
        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
    ) {
        Text("Verificar Email")
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyAccountPreview() {
    CoffeTechTheme {
        VerifyAccount(navController = NavController(LocalContext.current))
    }
}
