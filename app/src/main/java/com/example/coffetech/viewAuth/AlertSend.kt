
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffetech.R
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableDescriptionText


@Composable
fun AlertSend() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // Color de fondo claro
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            LogoImage()
            Spacer(modifier = Modifier.height(54.dp))
            ReusableDescriptionText(text = "Hemos enviado un correo electrónico para restablecer tu contraseña", fontSize = 20)
            Spacer(modifier = Modifier.height(16.dp))
            GoToEmailButton()
        }
    }
}



@Composable
fun GoToEmailButton() {
    TextButton(onClick = { /* Acción para ir al correo */ }) {
        Text(
            text = "Ir al correo",
            color = Color(0xFF4A4A4A), // Mismo color que el texto
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlertSend()
}

