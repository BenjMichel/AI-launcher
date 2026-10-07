package com.benjaminmichel.launcher.domain.usecase

import com.benjaminmichel.launcher.domain.model.App
import com.benjaminmichel.launcher.domain.repository.AppRepository
import javax.inject.Inject

class GetLaunchableAppsUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke(): List<App> = appRepository.getLaunchableApps()
}
