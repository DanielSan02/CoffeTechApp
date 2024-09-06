package com.example.coffetech.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
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
import androidx.compose.ui.zIndex
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
fun TopBarWithHamburger(
    onHamburgerClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(90.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = onHamburgerClick) {
            Icon(
                painter = painterResource(R.drawable.menu_icon),
                contentDescription = "Menu",
                tint = Color(0xFF2B2B2B)
            )
        }
        Spacer(modifier = Modifier.weight(1f)) // Espacio flexible para empujar el título al centro
        LargeText(text = title, fontSize = 20, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HamburgerMenu(
    profileImage: Painter,
    profileName: String,
    onProfileClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onHelpClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .zIndex(1f)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = profileImage,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = profileName, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        }
        TextButton(onClick = onProfileClick) {
            Text("Perfil")
        }
        TextButton(onClick = onNotificationsClick) {
            Text("Notificaciones")
        }
        TextButton(onClick = onHelpClick) {
            Text("Ayuda")
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onLogoutClick, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
            Text("Cerrar sesión", color = Color.White)
        }
    }
}

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit,
    onFincasClick: () -> Unit,
    onCentralButtonClick: () -> Unit,
    onReportsClick: () -> Unit,
    onCostsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = Color.White,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        IconButton(
            onClick = onHomeClick,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 9.dp)
                .size(70.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.home_icon),
                    contentDescription = "Inicio",
                    tint = Color(0xFF9A9A9A),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Inicio",
                    color = Color(0xFF9A9A9A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }

        IconButton(
            onClick = onFincasClick,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
                .size(70.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.fincas_icon),
                    contentDescription = "Fincas",
                    tint = Color(0xFF9A9A9A),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Fincas",
                    color = Color(0xFF9A9A9A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        Box(
            modifier = Modifier
                .offset(y = -8.dp)
                .clip(CircleShape)
                .size(56.dp)
                .background(Color(0xFFB31D34))
                .clickable(onClick = onCentralButtonClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.central_icon),
                contentDescription = "Central Button",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }


        Spacer(modifier = Modifier.weight(0.5f))

        IconButton(
            onClick = onReportsClick,
            modifier = Modifier
                .weight(1f)
                .size(70.dp)
                .padding(vertical = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.reports_icon),
                    contentDescription = "Reportes",
                    tint = Color(0xFF9A9A9A),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Reportes",
                    color = Color(0xFF9A9A9A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }

        IconButton(
            onClick = onCostsClick,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
                .size(70.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.cost_icon),
                    contentDescription = "Costos",
                    tint = Color(0xFF9A9A9A),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Costos",
                    color = Color(0xFF9A9A9A),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }
    }
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
