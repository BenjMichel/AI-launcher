package com.benjaminmichel.launcher.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.benjaminmichel.launcher.domain.model.App

/**
 * [style] defaults to the normal theme text color: this composable is also used inside the app
 * picker's opaque bottom sheet, which already has enough contrast on its own. The home screen's
 * app list (drawn directly over the wallpaper) overrides it with [com.benjaminmichel.launcher.ui.theme.WallpaperTextStyle].
 */
@Composable
fun AppListItem(
    app: App,
    onTap: (App) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Text(
        text = app.label,
        style = style,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTap(app) }
            .padding(horizontal = 24.dp, vertical = 16.dp),
    )
}
