package com.benjaminmichel.launcher.fakes

import com.benjaminmichel.launcher.domain.repository.AppLauncher

class FakeAppLauncher : AppLauncher {
    val launchedPackageNames = mutableListOf<String>()

    override fun launch(packageName: String): Boolean {
        launchedPackageNames += packageName
        return true
    }
}
