package com.benjaminmichel.launcher.fakes

import com.benjaminmichel.launcher.domain.model.App
import com.benjaminmichel.launcher.domain.repository.AppRepository

class FakeAppRepository(private var apps: List<App> = emptyList()) : AppRepository {
    override suspend fun getLaunchableApps(): List<App> = apps
}
