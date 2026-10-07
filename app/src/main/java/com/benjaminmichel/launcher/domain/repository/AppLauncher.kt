package com.benjaminmichel.launcher.domain.repository

interface AppLauncher {
    /** Returns true if an app matching [packageName] was launched. */
    fun launch(packageName: String): Boolean
}
