package com.benjaminmichel.launcher.domain.usecase

import com.benjaminmichel.launcher.domain.model.App
import com.benjaminmichel.launcher.domain.repository.FavoritesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Resolves each favorite slot's stored package name against the given [apps] list, yielding
 * `null` for empty slots or for a package name that no longer matches an installed app.
 */
class ObserveFavoriteAppsUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {
    operator fun invoke(apps: List<App>): Flow<List<App?>> =
        favoritesRepository.observeFavoriteSlots().map { slots ->
            slots.map { packageName -> apps.find { it.packageName == packageName } }
        }
}
