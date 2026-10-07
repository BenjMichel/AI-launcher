package com.benjaminmichel.launcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benjaminmichel.launcher.domain.model.App
import com.benjaminmichel.launcher.domain.usecase.AssignFavoriteUseCase
import com.benjaminmichel.launcher.domain.usecase.GetLaunchableAppsUseCase
import com.benjaminmichel.launcher.domain.usecase.LaunchAppUseCase
import com.benjaminmichel.launcher.domain.usecase.ObserveFavoriteAppsUseCase
import com.benjaminmichel.launcher.domain.usecase.SearchAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLaunchableApps: GetLaunchableAppsUseCase,
    private val observeFavoriteApps: ObserveFavoriteAppsUseCase,
    private val searchApps: SearchAppsUseCase,
    private val assignFavorite: AssignFavoriteUseCase,
    private val launchApp: LaunchAppUseCase,
) : ViewModel() {

    private val allApps = MutableStateFlow<List<App>>(emptyList())
    private val searchQuery = MutableStateFlow("")
    private val pickerTargetSlot = MutableStateFlow<Int?>(null)

    // Re-derived every time the installed-apps list changes, so it always resolves favorite
    // slots against the current app list as well as the persisted favorites store.
    private val favorites = allApps.flatMapLatest { apps -> observeFavoriteApps(apps) }

    val uiState: StateFlow<HomeUiState> = combine(
        allApps,
        favorites,
        searchQuery,
        pickerTargetSlot,
    ) { apps, favoriteApps, query, targetSlot ->
        HomeUiState(
            favorites = favoriteApps,
            searchQuery = query,
            filteredApps = searchApps(apps, query),
            pickerTargetSlot = targetSlot,
            pickerApps = apps,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    init {
        viewModelScope.launch {
            allApps.value = getLaunchableApps()
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    fun onFavoriteTap(app: App) {
        launchApp(app.packageName)
    }

    fun onFavoriteSlotLongPress(slot: Int) {
        pickerTargetSlot.value = slot
    }

    fun onPickerDismiss() {
        pickerTargetSlot.value = null
    }

    fun onPickerAppSelected(app: App) {
        val slot = pickerTargetSlot.value ?: return
        viewModelScope.launch {
            assignFavorite(slot, app.packageName)
        }
        pickerTargetSlot.value = null
    }

    fun onAppTap(app: App) {
        launchApp(app.packageName)
    }
}
