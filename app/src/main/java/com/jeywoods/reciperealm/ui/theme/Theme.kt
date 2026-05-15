package com.jeywoods.reciperealm.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//светлая тема по умолчанию
val LightColorSchemeDefault = lightColorScheme(
    primary = Black,
    onPrimary = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    error = ErrorLight,
    onError = White
)

//тёмная тема по умолчанию
val DarkColorSchemeDefault = darkColorScheme(
    primary = White,
    onPrimary = Black,
    background = DarkBackground,
    onBackground = White,
    surface = DarkSurface,
    onSurface = White,
    error = ErrorDark,
    onError = ErrorOnDark
)

//Светлые цветные темы

val LightColorSchemeMint = lightColorScheme(
    primary = MintPrimaryLight,
    onPrimary = White,
    background = MintBackgroundLight,
    onBackground = TextDark,
    surface = White,
    onSurface = TextDark,
    error = ErrorColorful,
    onError = White
)

val LightColorSchemeLavender = lightColorScheme(
    primary = LavenderPrimaryLight,
    onPrimary = White,
    background = LavenderBackgroundLight,
    onBackground = TextDark,
    surface = White,
    onSurface = TextDark,
    error = ErrorColorful,
    onError = White
)

val LightColorSchemeOcean = lightColorScheme(
    primary = OceanPrimaryLight,
    onPrimary = White,
    background = OceanBackgroundLight,
    onBackground = TextDark,
    surface = White,
    onSurface = TextDark,
    error = ErrorColorful,
    onError = White
)

val LightColorSchemeTerracotta = lightColorScheme(
    primary = TerracottaPrimaryLight,
    onPrimary = White,
    background = TerracottaBackgroundLight,
    onBackground = TextDark,
    surface = White,
    onSurface = TextDark,
    error = ErrorColorful,
    onError = White
)

val LightColorSchemeBerry = lightColorScheme(
    primary = BerryPrimaryLight,
    onPrimary = White,
    background = BerryBackgroundLight,
    onBackground = TextDark,
    surface = White,
    onSurface = TextDark,
    error = ErrorColorful,
    onError = White
)

//Тёмные цветные темы

val DarkColorSchemeMint = darkColorScheme(
    primary = MintPrimaryDark,
    onPrimary = Black,
    background = DarkBackgroundAlt,
    onBackground = White,
    surface = DarkSurfaceAlt,
    onSurface = White,
    error = ErrorColorfulDark,
    onError = Black
)

val DarkColorSchemeLavender = darkColorScheme(
    primary = LavenderPrimaryDark,
    onPrimary = Black,
    background = DarkBackgroundAlt,
    onBackground = White,
    surface = DarkSurfaceAlt,
    onSurface = White,
    error = ErrorColorfulDark,
    onError = Black
)

val DarkColorSchemeOcean = darkColorScheme(
    primary = OceanPrimaryDark,
    onPrimary = Black,
    background = DarkBackgroundAlt,
    onBackground = White,
    surface = DarkSurfaceAlt,
    onSurface = White,
    error = ErrorColorfulDark,
    onError = Black
)

val DarkColorSchemeTerracotta = darkColorScheme(
    primary = TerracottaPrimaryDark,
    onPrimary = Black,
    background = DarkBackgroundAlt,
    onBackground = White,
    surface = DarkSurfaceAlt,
    onSurface = White,
    error = ErrorColorfulDark,
    onError = Black
)

val DarkColorSchemeBerry = darkColorScheme(
    primary = BerryPrimaryDark,
    onPrimary = Black,
    background = DarkBackgroundAlt,
    onBackground = White,
    surface = DarkSurfaceAlt,
    onSurface = White,
    error = ErrorColorfulDark,
    onError = Black
)

enum class ColorTheme(val displayName: String) {
    DEFAULT("Default"),
    MINT("Mint"),
    LAVENDER("Lavender"),
    OCEAN("Ocean"),
    TERRACOTTA("Terracotta"),
    BERRY("Berry")
}

@Composable
fun RecipeRealmTheme(
    selectedColorTheme: ColorTheme = ColorTheme.DEFAULT,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme && selectedColorTheme == ColorTheme.DEFAULT -> DarkColorSchemeDefault
        darkTheme && selectedColorTheme == ColorTheme.MINT -> DarkColorSchemeMint
        darkTheme && selectedColorTheme == ColorTheme.LAVENDER -> DarkColorSchemeLavender
        darkTheme && selectedColorTheme == ColorTheme.OCEAN -> DarkColorSchemeOcean
        darkTheme && selectedColorTheme == ColorTheme.TERRACOTTA -> DarkColorSchemeTerracotta
        darkTheme && selectedColorTheme == ColorTheme.BERRY -> DarkColorSchemeBerry
        !darkTheme && selectedColorTheme == ColorTheme.MINT -> LightColorSchemeMint
        !darkTheme && selectedColorTheme == ColorTheme.LAVENDER -> LightColorSchemeLavender
        !darkTheme && selectedColorTheme == ColorTheme.OCEAN -> LightColorSchemeOcean
        !darkTheme && selectedColorTheme == ColorTheme.TERRACOTTA -> LightColorSchemeTerracotta
        !darkTheme && selectedColorTheme == ColorTheme.BERRY -> LightColorSchemeBerry
        else -> LightColorSchemeDefault
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = if (darkTheme) colorScheme.primary else White,
            darkIcons = !darkTheme
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}