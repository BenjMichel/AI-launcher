package com.benjaminmichel.launcher.domain.repository

import com.benjaminmichel.launcher.domain.model.App

interface AppRepository {
    suspend fun getLaunchableApps(): List<App>
}
