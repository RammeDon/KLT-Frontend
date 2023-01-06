package com.klt.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = KLTRed,
    primaryVariant = KLTRed,
    secondary = KLTRed
)

private val LightColorPalette = lightColors(
    primary = KLTRed,
    primaryVariant = KLTRed,
    secondary = KLTRed

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun KLTTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    // dark theme is now force disabled in app.
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
