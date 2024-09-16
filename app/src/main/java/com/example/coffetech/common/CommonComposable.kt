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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.viewmodel.farm.Farm

@Composable
fun ReusableButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = colors
    ) {
        Text(text)
    }
}

@Composable
fun FarmItem(farm: Farm, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFD7E8D2)) // Color verde claro
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Column {
            Text(
                text = farm.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = farm.area,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            )
        }
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
            .padding(16.dp),

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
fun ReusableSearchBar(
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    text: String
) {
    val cornerRadius = 28.dp

    Box(
        modifier = Modifier
            .size(width = 360.dp, height = 56.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(cornerRadius))
            .background(Color.Transparent, shape = RoundedCornerShape(cornerRadius)) // Background transparent to fit border
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(cornerRadius)
                ) // Fondo blanco del borde
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                value = query,
                onValueChange = onQueryChanged,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (query.text.isEmpty()) {
                            Text(
                                text = text,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espaciador entre el campo de texto y el ícono

            Icon(
                imageVector = Icons.Default.Search, // Puedes cambiar el ícono por el que prefieras
                contentDescription = "Search Icon",
                modifier = Modifier
                    .size(24.dp) // Tamaño del ícono
                    .align(Alignment.CenterVertically), // Alinea el ícono verticalmente en el centro
                tint = Color.Gray
            )
        }
    }
}


@Composable
fun FloatingActionButtonGroup(
    onMainButtonClick: () -> Unit,
    mainButtonIcon: Painter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Botón principal
        FloatingActionButton(
            onClick = onMainButtonClick,
            containerColor = Color(0xFFB31D34), // Ajusta el color si es necesario
            shape = androidx.compose.foundation.shape.CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                painter = mainButtonIcon,
                contentDescription = "Main Button",
                modifier = Modifier.size(24.dp), // Tamaño del ícono dentro del botón
                tint = Color.White
            )
        }
    }
}

@Composable
fun ReusableRoleDropdown(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onRoleSelected: (String) -> Unit,
    expandedArrowDropUp: Painter,
    arrowDropDown: Painter
) {
    val roles = listOf("Administrador", "Operador", "Dueño")
    var selectedRole by remember { mutableStateOf("Roles") }

    Box(modifier = Modifier
        .fillMaxWidth()
    ) {
        // Fondo blanco alrededor del botón
        Box(

            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 5.dp)
                .padding(horizontal = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(25.dp)) // Fondo blanco con esquinas redondeadas
                .border(1.dp, Color(0xD7FFFEFE), shape = RoundedCornerShape(25.dp)) // Borde gris alrededor del fondo
                .size(width = 95.dp, height = 40.dp) // Tamaño del área del botón
        ) {
            OutlinedButton(
                onClick = { onExpandedChange(!expanded) },
                modifier = Modifier
                    .fillMaxWidth(), // Ajusta el tamaño para llenar el área blanca
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(Color.White),
                ) {
                    Text(
                        text = selectedRole,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis, // Manejo del desbordamiento con puntos suspensivos
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = if (expanded) expandedArrowDropUp else arrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF5D8032)
                    )
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .background(Color.White),
        ) {
            roles.forEach { role ->
                DropdownMenuItem(
                    text = { Text(text = role) },
                    onClick = {
                        selectedRole = role
                        onRoleSelected(role)
                        onExpandedChange(false)
                    }
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
            .fillMaxWidth()
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




