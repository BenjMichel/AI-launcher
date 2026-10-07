package com.benjaminmichel.launcher.domain.usecase

import com.benjaminmichel.launcher.domain.repository.AppLauncher
import javax.inject.Inject

class LaunchAppUseCase @Inject constructor(
    private val appLauncher: AppLauncher,
) {
    operator fun invoke(packageName: String): Boolean = appLauncher.launch(packageName)
}
