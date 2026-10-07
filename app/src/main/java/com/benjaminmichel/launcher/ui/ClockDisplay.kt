package com.benjaminmichel.launcher.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.benjaminmichel.launcher.ui.theme.WallpaperTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val TimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

/**
 * Owns its own ticking state rather than reading it from a ViewModel: the current time is
 * ephemeral page chrome, not business state worth re-emitting through the app's state layer.
 */
@Composable
fun ClockDisplay(modifier: Modifier = Modifier) {
    var now by remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (isActive) {
            now = LocalTime.now()
            delay(1_000)
        }
    }

    Text(
        text = now.format(TimeFormatter),
        style = MaterialTheme.typography.displayLarge.merge(WallpaperTextStyle),
        modifier = modifier,
    )
}
