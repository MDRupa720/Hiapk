package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ZipDarkColorScheme =
    darkColorScheme(
        primary = PrimaryBlue,
        secondary = AccentViolet,
        tertiary = AccentCyan,
        background = BackgroundDark,
        surface = SurfaceDark,
        surfaceVariant = SurfaceVariantDark,
        onPrimary = TextPrimary,
        onSecondary = TextPrimary,
        onBackground = TextPrimary,
        onSurface = TextPrimary,
        error = ErrorRed
    )

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to premium dark theme for ZIP2APK Studio
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = ZipDarkColorScheme,
        typography = Typography,
        content = content
    )
}
