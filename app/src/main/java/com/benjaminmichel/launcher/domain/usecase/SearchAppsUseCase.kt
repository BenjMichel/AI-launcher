package com.benjaminmichel.launcher.domain.usecase

import com.benjaminmichel.launcher.domain.model.App
import javax.inject.Inject

class SearchAppsUseCase @Inject constructor() {
    operator fun invoke(apps: List<App>, query: String): List<App> {
        if (query.isBlank()) return apps
        return apps.filter { it.label.contains(query, ignoreCase = true) }
    }
}
