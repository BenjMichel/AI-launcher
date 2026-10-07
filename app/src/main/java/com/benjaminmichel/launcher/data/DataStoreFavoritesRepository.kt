package com.benjaminmichel.launcher.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.benjaminmichel.launcher.domain.repository.FavoritesRepository
import com.benjaminmichel.launcher.domain.repository.FavoritesRepository.Companion.FAVORITE_SLOT_COUNT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.favoritesDataStore by preferencesDataStore(name = "favorites")

class DataStoreFavoritesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) : FavoritesRepository {

    override fun observeFavoriteSlots(): Flow<List<String?>> =
        context.favoritesDataStore.data.map { preferences ->
            (0 until FAVORITE_SLOT_COUNT).map { slot -> preferences[slotKey(slot)] }
        }

    override suspend fun setFavorite(slot: Int, packageName: String) {
        context.favoritesDataStore.edit { preferences ->
            preferences[slotKey(slot)] = packageName
        }
    }

    private fun slotKey(slot: Int) = stringPreferencesKey("favorite_slot_$slot")
}
