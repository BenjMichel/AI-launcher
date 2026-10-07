package com.benjaminmichel.launcher.domain.usecase

import com.benjaminmichel.launcher.domain.model.App
import com.benjaminmichel.launcher.domain.repository.FavoritesRepository.Companion.FAVORITE_SLOT_COUNT
import com.benjaminmichel.launcher.fakes.FakeFavoritesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ObserveFavoriteAppsUseCaseTest {

    private val favoritesRepository = FakeFavoritesRepository()
    private val useCase = ObserveFavoriteAppsUseCase(favoritesRepository)

    private val apps = listOf(
        App(label = "Camera", packageName = "com.example.camera"),
        App(label = "Calendar", packageName = "com.example.calendar"),
    )

    @Test
    fun `unassigned slots resolve to null`() = runTest {
        val favorites = useCase(apps).first()

        assertEquals(FAVORITE_SLOT_COUNT, favorites.size)
        assertTrue(favorites.all { it == null })
    }

    @Test
    fun `assigned slot resolves to the matching installed app`() = runTest {
        favoritesRepository.setFavorite(slot = 0, packageName = "com.example.camera")

        val favorites = useCase(apps).first()

        assertEquals(apps[0], favorites[0])
        assertTrue(favorites.drop(1).all { it == null })
    }

    @Test
    fun `slot pointing to an uninstalled package resolves to null`() = runTest {
        favoritesRepository.setFavorite(slot = 0, packageName = "com.example.uninstalled")

        val favorites = useCase(apps).first()

        assertEquals(null, favorites[0])
    }
}
