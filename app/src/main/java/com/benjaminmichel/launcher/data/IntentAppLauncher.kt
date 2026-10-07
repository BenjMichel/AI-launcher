package com.benjaminmichel.launcher.data

import android.content.Context
import android.content.Intent
import com.benjaminmichel.launcher.domain.repository.AppLauncher
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class IntentAppLauncher @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppLauncher {

    override fun launch(packageName: String): Boolean {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName) ?: return false
        // context here is the application context (injected via @ApplicationContext), which
        // requires FLAG_ACTIVITY_NEW_TASK to start an activity outside of an activity context.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return true
    }
}
