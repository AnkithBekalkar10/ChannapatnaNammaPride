package com.channapatna.nammapride.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Premium Heritage Palette
val LacRed = Color(0xFFC62828)
val Turmeric = Color(0xFFFBC02D)
val LeafGreen = Color(0xFF2E7D32)
val Indigo = Color(0xFF1565C0)

val AppBackground = Color(0xFFFDFBFA) // Creamy, high-end paper feel
val Ink = Color(0xFF1C1B1F)
val SurfaceCream = Color(0xFFFFF9EE)

private val ColorScheme = lightColorScheme(
    primary = LacRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD6),
    onPrimaryContainer = Color(0xFF410002),
    secondary = LeafGreen,
    onSecondary = Color.White,
    tertiary = Indigo,
    onTertiary = Color.White,
    background = AppBackground,
    surface = Color.White,
    onBackground = Ink,
    onSurface = Ink,
    surfaceVariant = SurfaceCream
)

@Composable
fun ChannapatnaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
