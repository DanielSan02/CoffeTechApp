import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(navController: NavController) {
    // Variables de estado para los campos de entrada
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)), // Fondo gris claro
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
        ) {
            LogoImage()
            Spacer(modifier = Modifier.height(80.dp))
            ReusableDescriptionText(text = "Ingrese su nueva contraseña", fontSize = 25)
            Spacer(modifier = Modifier.height(16.dp))
            ReusableTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Contraseña",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            ReusableTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirmar contraseña",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            ReusableButton(text ="Restablecer" , onClick = { /*TODO*/ }, )
            CancelButton(navController = navController)
        }
    }
}
@Composable
fun CancelButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.LoginScreen)
        },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("Cancelar",
            color = Color(0xFF49602D))
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewResetPasswordScreen() {
    ResetPasswordScreen(navController = NavController(LocalContext.current))
}
