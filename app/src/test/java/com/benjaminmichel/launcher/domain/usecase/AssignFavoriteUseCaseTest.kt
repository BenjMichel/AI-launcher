package com.benjaminmichel.launcher.domain.usecase

import com.benjaminmichel.launcher.fakes.FakeFavoritesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class AssignFavoriteUseCaseTest {

    private val favoritesRepository = FakeFavoritesRepository()
    private val useCase = AssignFavoriteUseCase(favoritesRepository)

    @Test
    fun `assigning a slot persists the package name at that index`() = runTest {
        useCase(slot = 2, packageName = "com.example.spotify")

        val slots = favoritesRepository.observeFavoriteSlots().first()

        assertEquals("com.example.spotify", slots[2])
        assertEquals(null, slots[0])
    }

    @Test
    fun `reassigning a slot overwrites the previous package name`() = runTest {
        useCase(slot = 1, packageName = "com.example.camera")
        useCase(slot = 1, packageName = "com.example.calendar")

        val slots = favoritesRepository.observeFavoriteSlots().first()

        assertEquals("com.example.calendar", slots[1])
    }
}
