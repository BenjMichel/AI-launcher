package com.benjaminmichel.launcher.fakes

import com.benjaminmichel.launcher.domain.repository.FavoritesRepository
import com.benjaminmichel.launcher.domain.repository.FavoritesRepository.Companion.FAVORITE_SLOT_COUNT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeFavoritesRepository : FavoritesRepository {
    private val slots = MutableStateFlow<List<String?>>(List(FAVORITE_SLOT_COUNT) { null })

    override fun observeFavoriteSlots(): StateFlow<List<String?>> = slots

    override suspend fun setFavorite(slot: Int, packageName: String) {
        slots.value = slots.value.toMutableList().also { it[slot] = packageName }
    }
}
