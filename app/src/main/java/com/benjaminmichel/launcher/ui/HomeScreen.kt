package com.benjaminmichel.launcher.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.benjaminmichel.launcher.presentation.HomeViewModel
import com.benjaminmichel.launcher.ui.theme.WallpaperTextStyle

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    // A home-screen launcher never "closes" on back — except to dismiss an open picker sheet,
    // which ModalBottomSheet already handles natively.
    BackHandler(enabled = uiState.pickerTargetSlot == null) {}

    // Transparent so the system wallpaper (shown via windowShowWallpaper in the theme) is
    // visible behind the UI, like a real launcher rather than an opaque screen.
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            ClockDisplay()

            FavoritesRow(
                favorites = uiState.favorites,
                onAppTap = viewModel::onFavoriteTap,
                onSlotPickerRequest = viewModel::onFavoriteSlotLongPress,
            )

            SearchField(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
            )

            LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
                items(uiState.filteredApps, key = { it.packageName }) { app ->
                    AppListItem(
                        app = app,
                        onTap = viewModel::onAppTap,
                        style = MaterialTheme.typography.bodyLarge.merge(WallpaperTextStyle),
                    )
                }
            }
        }
    }

    if (uiState.pickerTargetSlot != null) {
        AppPickerSheet(
            apps = uiState.pickerApps,
            onAppSelected = viewModel::onPickerAppSelected,
            onDismiss = viewModel::onPickerDismiss,
        )
    }
}
