package com.example.coffetech.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffetech.R

@Composable
fun ReusableButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = Color(0xFF49602D),
    contentColor: Color = Color.White
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = contentColor),
        modifier = modifier
    ) {
        Text(text)
    }
}
@Composable
fun ReusableTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isValid: Boolean = true,
    maxWidth: Dp = 300.dp, // Ancho máximo predeterminado
    maxHeight: Dp = 80.dp,
    margin: Dp = 8.dp, // Margen predeterminado
    errorMessage: String = "" // Añadido para el mensaje de error
) {
    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1, // Limitar a una sola línea
            modifier = modifier
                .padding(margin) // Aplicar margen alrededor del TextField
                .border(1.dp, if (isValid) Color.Gray else Color.Red, RoundedCornerShape(4.dp))
                .widthIn(max = maxWidth) // Limitar el ancho máximo del TextField
                .width(maxWidth)
                .heightIn(max = maxHeight)
                .horizontalScroll(rememberScrollState()) // Habilitar desplazamiento horizontal
        )
        if (!isValid && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}



@Composable
fun LargeText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 40,
    fontWeight: FontWeight = FontWeight.W800,
    color: Color = Color(0xFF31373E)
) {
    Text(
        text = text,
        style = TextStyle(fontSize = fontSize.sp, fontWeight = fontWeight),
        color = color,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ReusableDescriptionText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 17,
    fontWeight: FontWeight = FontWeight.W300,
    textAlign: TextAlign = TextAlign.Center,
    fontFamily: FontFamily = FontFamily.Default,
    paddingValues: PaddingValues = PaddingValues(bottom = 15.dp, top = 5.dp, start = 10.dp, end = 10.dp)
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = fontFamily,
            fontSize = fontSize.sp,
            fontWeight = fontWeight
        ),
        textAlign = textAlign,
        modifier = modifier.padding(paddingValues)
    )
}

@Composable
fun LogoImage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(width = 205.dp, height = 212.dp)
    ) {
        Box(
            modifier = Modifier
                .size(width = 205.dp, height = 212.dp)
                .offset(y = 5.dp)
                .graphicsLayer {
                    shadowElevation = 10.dp.toPx()
                    shape = CircleShape
                    clip = true
                    alpha = 3f
                }
                .background(Color.Transparent)
        )

        Image(
            painter = painterResource(R.drawable.logored),
            contentDescription = "Logo",
            modifier = Modifier
                .size(width = 205.dp, height = 212.dp)
                .clip(CircleShape)
                .background(Color.White)



        )
    }
}
