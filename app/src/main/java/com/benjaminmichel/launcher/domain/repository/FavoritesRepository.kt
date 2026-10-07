package com.benjaminmichel.launcher.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Slots are addressed by index (0..FAVORITE_SLOT_COUNT - 1). A `null` entry means the slot
 * has no app assigned yet.
 */
interface FavoritesRepository {
    fun observeFavoriteSlots(): Flow<List<String?>>
    suspend fun setFavorite(slot: Int, packageName: String)

    companion object {
        const val FAVORITE_SLOT_COUNT = 5
    }
}
