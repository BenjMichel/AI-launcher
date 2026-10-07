package com.benjaminmichel.launcher.presentation

import com.benjaminmichel.launcher.domain.model.App
import com.benjaminmichel.launcher.domain.repository.FavoritesRepository.Companion.FAVORITE_SLOT_COUNT

/**
 * The clock display is intentionally not part of this state: it's ephemeral, page-local UI
 * chrome with no business meaning, so it's owned directly by the composable that renders it
 * instead of ticking through the ViewModel every second.
 */
data class HomeUiState(
    val favorites: List<App?> = List(FAVORITE_SLOT_COUNT) { null },
    val searchQuery: String = "",
    val filteredApps: List<App> = emptyList(),
    val pickerTargetSlot: Int? = null,
    val pickerApps: List<App> = emptyList(),
)
