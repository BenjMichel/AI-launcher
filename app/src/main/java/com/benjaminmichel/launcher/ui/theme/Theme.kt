package com.benjaminmichel.launcher.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LauncherDarkColors = darkColorScheme(
    background = Color(0xFF0B0B0D),
    surface = Color(0xFF0B0B0D),
    onBackground = Color(0xFFF2F2F2),
    onSurface = Color(0xFFF2F2F2),
)

private val LauncherLightColors = lightColorScheme(
    background = Color(0xFFF2F2F2),
    surface = Color(0xFFF2F2F2),
    onBackground = Color(0xFF0B0B0D),
    onSurface = Color(0xFF0B0B0D),
)

@Composable
fun LauncherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) LauncherDarkColors else LauncherLightColors
    MaterialTheme(colorScheme = colorScheme, content = content)
}
