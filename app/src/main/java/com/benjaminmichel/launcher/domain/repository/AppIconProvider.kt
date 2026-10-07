package com.benjaminmichel.launcher.domain.repository

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Depends on a Compose UI type ([ImageBitmap]) rather than a raw platform type (e.g. `Bitmap`).
 * A pragmatic compromise for this project's size: it keeps the domain free of low-level
 * Android framework types while avoiding an extra mapping layer purely for the icon's pixels.
 */
interface AppIconProvider {
    fun icon(packageName: String): ImageBitmap?
}
