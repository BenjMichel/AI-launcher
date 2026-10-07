package com.benjaminmichel.launcher.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle

/**
 * Merged into the typography style of any text drawn directly over the transparent wallpaper
 * background (clock, favorites, app list) so it stays legible regardless of the wallpaper's
 * own brightness. Not used inside opaque surfaces (the app picker sheet), where the normal
 * theme text color already has enough contrast.
 */
val WallpaperTextStyle = TextStyle(
    color = Color.White,
    shadow = Shadow(
        color = Color.Black.copy(alpha = 0.6f),
        offset = Offset(0f, 2f),
        blurRadius = 6f,
    ),
)
