package com.benjaminmichel.launcher.domain.usecase

import com.benjaminmichel.launcher.domain.model.App
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchAppsUseCaseTest {

    private val useCase = SearchAppsUseCase()
    private val apps = listOf(
        App(label = "Camera", packageName = "com.example.camera"),
        App(label = "Calendar", packageName = "com.example.calendar"),
        App(label = "Spotify", packageName = "com.example.spotify"),
    )

    @Test
    fun `blank query returns the full list`() {
        assertEquals(apps, useCase(apps, ""))
    }

    @Test
    fun `query filters case-insensitively by label`() {
        assertEquals(listOf("Camera"), useCase(apps, "CAM").map { it.label })
    }

    @Test
    fun `query matching several labels returns all of them`() {
        assertEquals(listOf("Camera", "Calendar"), useCase(apps, "ca").map { it.label })
    }

    @Test
    fun `query matching nothing returns an empty list`() {
        assertTrue(useCase(apps, "zzz").isEmpty())
    }
}
