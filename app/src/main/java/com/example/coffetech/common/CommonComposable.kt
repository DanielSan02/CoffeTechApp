package com.example.coffetech.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
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
fun TopBarWithBackArrow(
    onBackClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(90.dp)
            .height(56.dp)
            .padding( 16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = "Back",
                tint = Color(0xFF2B2B2B),
                modifier = Modifier.size(24.dp)

            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W800),
            color = Color(0xFF2B2B2B)
        )
        Spacer(modifier = Modifier.weight(1f))
    }

}

@Composable
fun ReusableTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isPassword: Boolean = false,
    isValid: Boolean = true,
    maxWidth: Dp = 300.dp,
    maxHeight: Dp = 80.dp,
    margin: Dp = 8.dp,
    errorMessage: String = ""
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            enabled = enabled,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Gray.copy(alpha = 0.6f), // Texto más claro cuando está deshabilitado
                disabledPlaceholderColor = Color.Gray.copy(alpha = 0.6f) // Placeholder más claro cuando está deshabilitado
            ),

            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                            ),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            },
            maxLines = 1,
            modifier = modifier
                .padding(margin)
                .border(
                    1.dp,
                    when {
                        !enabled -> Color.Gray.copy(alpha = 0.3f) // Borde más claro cuando está deshabilitado
                        !isValid -> Color.Red
                        else -> Color.Gray
                    },
                    RoundedCornerShape(4.dp)
                )
                .widthIn(max = maxWidth)
                .width(maxWidth)
                .heightIn(max = maxHeight)
                .horizontalScroll(rememberScrollState())
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

// Vista base de topbar y bottombar


@Composable
fun TopBarWithHamburger(
    onHamburgerClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .size(90.dp)
            .background(Color.White)
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
                    tint = Color(0xFFB31D34),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Fincas",
                    color = Color(0xFFB31D34),
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
fun BaseScreen(
    modifier: Modifier = Modifier,
    title: String,
    navController: NavController,
    isMenuVisible: Boolean = false,
    onHamburgerClick: () -> Unit = {},
    onHomeClick: () -> Unit = { navController.navigate("ruta_de_inicio") },
    onFincasClick: () -> Unit = { navController.navigate("ruta_de_fincas") },
    onCentralButtonClick: () -> Unit = { /* Acción por defecto para el botón central */ },
    onReportsClick: () -> Unit = { navController.navigate("ruta_de_reportes") },
    onCostsClick: () -> Unit = { navController.navigate("ruta_de_costos") },
    onProfileClick: () -> Unit = { navController.navigate("ruta_de_perfil") },
    onNotificationsClick: () -> Unit = { navController.navigate("ruta_de_notificaciones") },
    onHelpClick: () -> Unit = { navController.navigate("ruta_de_ayuda") },
    onLogoutClick: () -> Unit = { navController.navigate("ruta_de_login") },
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarWithHamburger(
                onHamburgerClick = onHamburgerClick,
                title = title
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = onHomeClick,
                onFincasClick = onFincasClick,
                onCentralButtonClick = onCentralButtonClick,
                onReportsClick = onReportsClick,
                onCostsClick = onCostsClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
            // Mostrar menú hamburguesa si está visible
                if (isMenuVisible) {
                    HamburgerMenu(
                        profileImage = painterResource(id = R.drawable.menu_icon), // Esto es una función Composable
                        profileName = "Usuario",
                        onProfileClick = onProfileClick,
                        onNotificationsClick = onNotificationsClick,
                        onHelpClick = onHelpClick,
                        onLogoutClick = onLogoutClick  // Este ya no es Composable
                    )
                }
            }
        }
    }




// end



@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit
) {
    val cornerRadius = 28.dp

    Box(
        modifier = Modifier
            .size(width = 360.dp, height = 56.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(cornerRadius))
            .background(Color.Transparent, shape = RoundedCornerShape(cornerRadius)) // Background transparent to fit border
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, shape = RoundedCornerShape(cornerRadius)) // White background inside the border
                .padding(horizontal = 16.dp) // Padding to prevent text from touching the border
        ) {
            BasicTextField(
                value = query,
                onValueChange = onQueryChanged,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (query.text.isEmpty()) {
                            Text(
                                text = "Search by farm name",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun FloatingActionButtonGroup(
    onMainButtonClick: () -> Unit,
    onSubButton1Click: () -> Unit,
    onSubButton2Click: () -> Unit,
    subButton1Icon: Painter,
    subButton2Icon: Painter,
    mainButtonIcon: Painter,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (expanded) {
                // Botón secundario 2
                Box(
                    modifier = Modifier
                        .offset(-5.dp)
                        .clip(shape = CircleShape)
                        .border(width = 2.dp, color = Color(0xFFE9E9F2), shape = RoundedCornerShape(28.dp))
                        .size(44.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = {
                            expanded = false
                            onSubButton2Click()
                        },
                        shape = CircleShape,
                        containerColor = Color.White, // Ajusta el color si es necesario
                    ) {
                        Icon(
                            painter = subButton2Icon,
                            contentDescription = "Sub Button 2",
                            modifier = Modifier
                                .size(17.dp) // Tamaño del ícono dentro del botón
                        )
                    }
                }

                // Botón secundario 1
                Box(
                    modifier = Modifier
                        .offset(x = 10.dp)
                        .padding(15.dp)
                        .clip(shape = CircleShape)
                        .border(width = 2.dp, color = Color(0xFFE9E9F2), shape = RoundedCornerShape(28.dp))
                        .size(44.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = {
                            expanded = false
                            onSubButton1Click()
                        },
                        shape = CircleShape,
                        containerColor = Color.White,
                    ) {
                        Icon(
                            painter = subButton1Icon,
                            contentDescription = "Sub Button 1",
                            modifier = Modifier
                                .size(17.dp), // Tamaño del ícono dentro del botón
                        )
                    }
                }
            }

            // Botón principal
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(shape = CircleShape),// Tamaño del contenedor para asegurar la forma circular
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = {
                        expanded = !expanded
                        onMainButtonClick()
                    },
                    containerColor = Color(0xFFB31D34), // Ajusta el color si es necesario
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Icon(
                        painter = mainButtonIcon,
                        contentDescription = "Main Button",
                        modifier = Modifier.size(20.dp), // Tamaño del ícono dentro del botón
                        tint = Color.White,
                    )
                }
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
@Composable
fun ReusableFieldLabel(
    text: String,
    modifier: Modifier = Modifier // Permitir pasar un modificador opcional
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black,
        fontSize = 16.sp,
        modifier = modifier
            .fillMaxWidth() // Asegurarse de que la etiqueta ocupe todo el ancho disponible
            .padding(bottom = 8.dp) // Ajustar el padding inferior (puedes ajustarlo según sea necesario)
    )
}

@Composable
fun ReusableCancelButton(
    navController: NavController,
    destination: String,  // Ruta a la que navegará cuando se presione cancelar
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = { navController.navigate(destination) },
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Text("Cancelar", color = Color(0xFF49602D))
    }
}


