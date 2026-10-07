package com.benjaminmichel.launcher.data

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.benjaminmichel.launcher.domain.repository.AppIconProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PackageManagerIconProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppIconProvider {

    override fun icon(packageName: String): ImageBitmap? = try {
        context.packageManager
            .getApplicationIcon(packageName)
            .toBitmap()
            .asImageBitmap()
    } catch (e: android.content.pm.PackageManager.NameNotFoundException) {
        null
    }
}
