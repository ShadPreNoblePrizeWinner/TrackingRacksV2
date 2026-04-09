package com.finistro.trackingracks.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SteampunkColorScheme = darkColorScheme(
    primary = Brass,
    secondary = Copper,
    background = InputBg,
    surface = ContainerBg,
    onPrimary = IronDark,
    onSecondary = IronDark,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun SteampunkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SteampunkColorScheme,
        typography = SteampunkTypography,
        content = content
    )
}
