package com.benjaminmichel.launcher.ui

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.benjaminmichel.launcher.R
import com.benjaminmichel.launcher.domain.model.App
import com.benjaminmichel.launcher.ui.theme.WallpaperTextStyle

/**
 * Tap on an assigned slot launches the app; tap on an empty slot opens the picker directly
 * (no need for a long-press when there's nothing to launch yet). Long-press always opens the
 * picker, letting the user change an already-assigned slot.
 */
@Composable
fun FavoritesRow(
    favorites: List<App?>,
    onAppTap: (App) -> Unit,
    onSlotPickerRequest: (slot: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        favorites.forEachIndexed { slot, app ->
            FavoriteSlot(
                app = app,
                onTap = {
                    if (app != null) onAppTap(app) else onSlotPickerRequest(slot)
                },
                onLongPress = { onSlotPickerRequest(slot) },
            )
        }
    }
}

@Composable
private fun FavoriteSlot(
    app: App?,
    onTap: () -> Unit,
    onLongPress: () -> Unit,
) {
    Box(
        modifier = Modifier
            .combinedClickable(onClick = onTap, onLongClick = onLongPress)
            .padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (app == null) {
            Text(
                text = stringResource(R.string.empty_favorite_slot),
                style = MaterialTheme.typography.headlineSmall.merge(WallpaperTextStyle),
            )
        } else {
            Text(
                text = app.label,
                style = MaterialTheme.typography.bodyLarge.merge(WallpaperTextStyle),
            )
        }
    }
}
