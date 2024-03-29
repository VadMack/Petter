package ru.gortea.petter.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Primary600,
    primaryVariant = Primary500,
    onPrimary = Base0,
    secondary = Secondary700,
    background = Background,
    onBackground = Base900,
    error = Error,
    surface = Background,
    onSurface = Base900
)

private val LightColorPalette = lightColors(
    primary = Primary600,
    primaryVariant = Primary500,
    onPrimary = Base0,
    secondary = Secondary700,
    background = Background,
    onBackground = Base900,
    error = Error,
    surface = Background,
    onSurface = Base900
)

@Composable
fun PetterAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
