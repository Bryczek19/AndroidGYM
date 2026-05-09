package com.example.fitloyalty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(

    primary = GoldPrimary,
    secondary = GoldDark,

    background = BackgroundWhite,
    surface = CardWhite,

    onPrimary = TextBlack,
    onSecondary = TextBlack,

    onBackground = TextBlack,
    onSurface = TextBlack
)

private val DarkColors = darkColorScheme(

    primary = GoldPrimary,
    secondary = GoldDark,

    background = BackgroundWhite,
    surface = CardWhite,

    onPrimary = TextBlack,
    onSecondary = TextBlack,

    onBackground = TextBlack,
    onSurface = TextBlack
)

@Composable
fun FitLoyaltyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}