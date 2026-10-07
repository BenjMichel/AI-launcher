package com.benjaminmichel.launcher.presentation

import app.cash.turbine.test
import com.benjaminmichel.launcher.MainDispatcherRule
import com.benjaminmichel.launcher.domain.model.App
import com.benjaminmichel.launcher.domain.usecase.AssignFavoriteUseCase
import com.benjaminmichel.launcher.domain.usecase.GetLaunchableAppsUseCase
import com.benjaminmichel.launcher.domain.usecase.LaunchAppUseCase
import com.benjaminmichel.launcher.domain.usecase.ObserveFavoriteAppsUseCase
import com.benjaminmichel.launcher.domain.usecase.SearchAppsUseCase
import com.benjaminmichel.launcher.fakes.FakeAppLauncher
import com.benjaminmichel.launcher.fakes.FakeAppRepository
import com.benjaminmichel.launcher.fakes.FakeFavoritesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val camera = App(label = "Camera", packageName = "com.example.camera")
    private val calendar = App(label = "Calendar", packageName = "com.example.calendar")

    private val appRepository = FakeAppRepository(listOf(camera, calendar))
    private val favoritesRepository = FakeFavoritesRepository()
    private val appLauncher = FakeAppLauncher()

    private fun createViewModel() = HomeViewModel(
        getLaunchableApps = GetLaunchableAppsUseCase(appRepository),
        observeFavoriteApps = ObserveFavoriteAppsUseCase(favoritesRepository),
        searchApps = SearchAppsUseCase(),
        assignFavorite = AssignFavoriteUseCase(favoritesRepository),
        launchApp = LaunchAppUseCase(appLauncher),
    )

    @Test
    fun `initial state exposes the full installed app list once loaded`() = runTest {
        createViewModel().uiState.test {
            skipItems(1) // initial empty HomeUiState, before the app list finishes loading
            val loaded = awaitItem()
            assertEquals(listOf(camera, calendar), loaded.filteredApps)
        }
    }

    @Test
    fun `search query filters the app list case-insensitively`() = runTest {
        val viewModel = createViewModel()
        viewModel.uiState.test {
            skipItems(1)
            awaitItem() // apps loaded

            viewModel.onSearchQueryChange("cam")

            val filtered = awaitItem()
            assertEquals(listOf(camera), filtered.filteredApps)
        }
    }

    @Test
    fun `long-pressing a favorite slot opens the picker for that slot`() = runTest {
        val viewModel = createViewModel()
        viewModel.uiState.test {
            skipItems(1)
            awaitItem() // apps loaded

            viewModel.onFavoriteSlotLongPress(2)

            assertEquals(2, awaitItem().pickerTargetSlot)
        }
    }

    @Test
    fun `selecting an app in the picker assigns it to the target slot and closes the picker`() =
        runTest {
            val viewModel = createViewModel()
            viewModel.uiState.test {
                skipItems(1)
                awaitItem() // apps loaded

                viewModel.onFavoriteSlotLongPress(0)
                awaitItem() // picker open

                viewModel.onPickerAppSelected(camera)

                // Dismissing the picker (pickerTargetSlot -> null) and the persisted favorite
                // becoming visible both go through the same combined uiState flow.
                val closed = awaitItem()
                assertNull(closed.pickerTargetSlot)

                val withFavorite = awaitItem()
                assertEquals(camera, withFavorite.favorites[0])
            }
        }

    @Test
    fun `tapping an assigned favorite launches it instead of opening the picker`() = runTest {
        val viewModel = createViewModel()
        favoritesRepository.setFavorite(slot = 0, packageName = camera.packageName)

        viewModel.onFavoriteTap(camera)

        assertEquals(listOf(camera.packageName), appLauncher.launchedPackageNames)
    }
}
