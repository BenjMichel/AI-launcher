package com.benjaminmichel.launcher.domain.usecase

import com.benjaminmichel.launcher.domain.repository.FavoritesRepository
import javax.inject.Inject

class AssignFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) {
    suspend operator fun invoke(slot: Int, packageName: String) {
        favoritesRepository.setFavorite(slot, packageName)
    }
}
