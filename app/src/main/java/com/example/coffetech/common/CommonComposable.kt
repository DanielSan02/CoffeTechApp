package com.example.coffetech.common

import Farm
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.ui.theme.CoffeTechTheme

@Composable
fun ReusableButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonType: ButtonType = ButtonType.Red, // Parámetro para elegir el tipo de botón
    minHeight: Dp = 56.dp,  // Alto mínimo predeterminado
    minWidth: Dp = 160.dp,  // Ancho mínimo predeterminado
    maxWidth: Dp = 300.dp   // Ancho máximo predeterminado
) {
    val buttonColors = when (buttonType) {
        ButtonType.Red -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,  // Fondo rojo
            contentColor = MaterialTheme.colorScheme.onPrimary   // Texto blanco sobre fondo rojo
        )
        ButtonType.Green -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,  // Fondo verde
            contentColor = MaterialTheme.colorScheme.onSecondary   // Texto blanco sobre fondo verde
        )
        ButtonType.JustText -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,  // Fondo transparente
            contentColor = MaterialTheme.colorScheme.primary  // Texto rojo sin fondo
        )
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .heightIn(min = minHeight)  // Establecer alto mínimo y máximo
            .widthIn(min = minWidth, max = maxWidth),    // Establecer ancho mínimo y máximo
        colors = buttonColors
    ) {
        Text(text=text,
            style = MaterialTheme.typography.bodyLarge  // Aplicar estilo de texto para el botón
        )
    }
}


// Enum para los diferentes tipos de botones
enum class ButtonType {
    Red,    // Botón rojo
    Green,  // Botón verde
    JustText  // Solo texto sin fondo
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
            .height(90.dp)
            .padding(16.dp)
            .statusBarsPadding(), // Ajusta el padding para la barra de estado (notificaciones)


        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = "Back",
                tint = Color(0xFF2B2B2B),
                modifier = Modifier.size(56.dp)

            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
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
    maxHeight: Dp = 1000.dp,
    margin: Dp = 8.dp,
    errorMessage: String = "",
    charLimit: Int = 100, // Límite de caracteres por defecto a 100
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column {
        TextField(
            value = value.take(charLimit), // Limita la cantidad de caracteres a charLimit
            onValueChange = {
                if (it.length <= charLimit) {
                    onValueChange(it) // Solo permite cambios si no excede el límite
                }
            },
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
                disabledTextColor = Color.Gray.copy(alpha = 0.6f),
                disabledPlaceholderColor = Color.Gray.copy(alpha = 0.6f)
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
                        !enabled -> Color.Gray.copy(alpha = 0.3f)
                        !isValid -> Color.Red
                        else -> Color.Gray
                    },
                    RoundedCornerShape(4.dp)
                )
                .widthIn(max = maxWidth)
                .width(maxWidth)
                .heightIn(min = 56.dp, max = maxHeight) // Altura mínima de 56.dp
                .horizontalScroll(scrollState)
        )

        LaunchedEffect(value) {
            // Cuando cambie el valor, desplázate al final del texto
            scrollState.scrollTo(scrollState.maxValue)
        }

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
fun ReusableTittleLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF31373E)
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ReusableTittleSmall(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 1, // Limitar las líneas
    overflow: TextOverflow = TextOverflow.Ellipsis // Cortar texto largo
) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow,
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun ReusableDescriptionText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    maxWidth: Dp = 300.dp // Ancho máximo configurable
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = textAlign,
        modifier = modifier
            .fillMaxWidth() // Hace que el componente ocupe todo el ancho disponible
            .widthIn(max = maxWidth) // Establece el ancho máximo del texto
    )
}


// Vista base de topbar y bottombar

@Composable
fun ReusableSearchBar(
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    text: String,
    modifier: Modifier = Modifier, // Permitir que se pase un modificador externo
    maxWidth: Dp = 300.dp, // Ancho máximo configurable como en ReusableTextField
    cornerRadius: Dp = 28.dp
) {
    Box(
        modifier = modifier
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(cornerRadius))
            .background(Color.Transparent, shape = RoundedCornerShape(cornerRadius))
            .widthIn(max = maxWidth) // Configura el ancho máximo igual que en ReusableTextField
            .height(56.dp) // Mantiene la altura fija en 56.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(cornerRadius)
                )
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
                                style = MaterialTheme.typography.bodyMedium
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
fun LogoImage(
    modifier: Modifier = Modifier.size(150.dp) // Tamaño por defecto de 150.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier // Se usa el `modifier` pasado a la función
    ) {
        Box(
            modifier = Modifier
                .matchParentSize() // Para que el tamaño interno coincida con el `modifier` de `LogoImage`
                .offset(y = 5.dp)
                .shadow(10.dp, CircleShape)
                .clip(CircleShape)
                .background(Color.Transparent)
        )

        Image(
            painter = painterResource(R.drawable.logored),
            contentDescription = "Logo",
            modifier = Modifier
                .matchParentSize() // La imagen también se ajusta al tamaño del contenedor
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
fun ReusableTextButton(
    navController: NavController,
    destination: String,  // Ruta a la que navegará cuando se presione cancelar
    modifier: Modifier = Modifier,
    text: String = "Cancelar",
    minWidth: Dp = 160.dp,  // Ancho mínimo predeterminado
    maxWidth: Dp = 300.dp,
) {
    TextButton(
        onClick = { navController.navigate(destination) },
        modifier = modifier
            .padding(bottom = 16.dp)
            .heightIn(min = 56.dp) // Altura mínima de 56dp
            .widthIn(min = minWidth, max = maxWidth) // Ancho fijo de 300dp

    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF49602D),
            textAlign = TextAlign.Center,
        )
    }
}





@Preview(showBackground = true)
@Composable
fun ReusableButtonPreview() {
    CoffeTechTheme {
        ReusableButton(
            text = "Login",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarWithBackArrowPreview() {
    CoffeTechTheme {
        TopBarWithBackArrow(
            onBackClick = {},
            title = "Title"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReusableTextFieldPreview() {
    CoffeTechTheme {
        ReusableTextField(
            value = "",
            onValueChange = {},
            placeholder = "Enter your text"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReusableTittleLargePreview() {
    CoffeTechTheme {
        ReusableTittleLarge(text = "Welcome")
    }
}
@Preview(showBackground = true)
@Composable
fun ReusableTittleSmallPreview() {
    CoffeTechTheme {
        ReusableTittleSmall(text = "Welcome")
    }
}


@Preview(showBackground = true)
@Composable
fun ReusableDescriptionTextPreview() {
    CoffeTechTheme {
        ReusableDescriptionText(text = "This is a description text.")
    }
}

@Preview(showBackground = true)
@Composable
fun ReusableSearchBarPreview() {
    CoffeTechTheme {
        ReusableSearchBar(
            query = TextFieldValue(""),
            onQueryChanged = {},
            text = "Search..."
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingActionButtonGroupPreview() {
    CoffeTechTheme {
        FloatingActionButtonGroup(
            onMainButtonClick = {},
            mainButtonIcon = painterResource(R.drawable.ic_launcher_foreground)  // Cambiar por tu recurso de ícono
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogoImagePreview() {
    CoffeTechTheme {
        LogoImage()
    }
}

@Preview(showBackground = true)
@Composable
fun ReusableFieldLabelPreview() {
    CoffeTechTheme {
        ReusableFieldLabel(text = "Label")
    }
}

@Preview(showBackground = true)
@Composable
fun ReusableCancelButtonPreview() {
    CoffeTechTheme {
        val navController = NavController(LocalContext.current)
        ReusableTextButton(
            navController = navController,
            destination = "home"
        )
    }
}
