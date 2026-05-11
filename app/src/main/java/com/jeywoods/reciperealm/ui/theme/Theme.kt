package com.jeywoods.reciperealm.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ========== СВЕТЛАЯ ТЕМА ПО УМОЛЧАНИЮ ==========
val LightColorSchemeDefault = lightColorScheme(
    primary = Color(0xFF000000),
    onPrimary = Color.White,
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    surface = Color(0xFFFFFFFF),
    onSurface = Color.Black,
    error = Color(0xFFBA1A1A),
    onError = Color.White
)

// ========== ТЁМНАЯ ТЕМА ПО УМОЛЧАНИЮ ==========
val DarkColorSchemeDefault = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    onPrimary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410)
)

// ========== СВЕТЛЫЕ ЦВЕТНЫЕ ТЕМЫ ==========
val LightColorSchemeMint = lightColorScheme(
    primary = Color(0xFF14B8A6),
    onPrimary = Color.White,
    background = Color(0xFFE6FFFA),
    onBackground = Color(0xFF1F2937),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1F2937),
    error = Color(0xFFEF4444),
    onError = Color.White
)

val LightColorSchemeLavender = lightColorScheme(
    primary = Color(0xFF8B5CF6),
    onPrimary = Color.White,
    background = Color(0xFFFAF5FF),
    onBackground = Color(0xFF1F2937),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1F2937),
    error = Color(0xFFEF4444),
    onError = Color.White
)

val LightColorSchemeOcean = lightColorScheme(
    primary = Color(0xFF3B82F6),
    onPrimary = Color.White,
    background = Color(0xFFEFF6FF),
    onBackground = Color(0xFF1F2937),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1F2937),
    error = Color(0xFFEF4444),
    onError = Color.White
)

val LightColorSchemeTerracotta = lightColorScheme(
    primary = Color(0xFFEA580C),
    onPrimary = Color.White,
    background = Color(0xFFFFF7ED),
    onBackground = Color(0xFF1F2937),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1F2937),
    error = Color(0xFFEF4444),
    onError = Color.White
)

val LightColorSchemeBerry = lightColorScheme(
    primary = Color(0xFFDB2777),
    onPrimary = Color.White,
    background = Color(0xFFFFF1F2),
    onBackground = Color(0xFF1F2937),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1F2937),
    error = Color(0xFFEF4444),
    onError = Color.White
)

// ========== ТЁМНЫЕ ЦВЕТНЫЕ ТЕМЫ ==========
val DarkColorSchemeMint = darkColorScheme(
    primary = Color(0xFF2DD4BF),
    onPrimary = Color.Black,
    background = Color(0xFF0A0A0A),
    onBackground = Color.White,
    surface = Color(0xFF1A1A1A),
    onSurface = Color.White,
    error = Color(0xFFF87171),
    onError = Color.Black
)

val DarkColorSchemeLavender = darkColorScheme(
    primary = Color(0xFFA78BFA),
    onPrimary = Color.Black,
    background = Color(0xFF0A0A0A),
    onBackground = Color.White,
    surface = Color(0xFF1A1A1A),
    onSurface = Color.White,
    error = Color(0xFFF87171),
    onError = Color.Black
)

val DarkColorSchemeOcean = darkColorScheme(
    primary = Color(0xFF60A5FA),
    onPrimary = Color.Black,
    background = Color(0xFF0A0A0A),
    onBackground = Color.White,
    surface = Color(0xFF1A1A1A),
    onSurface = Color.White,
    error = Color(0xFFF87171),
    onError = Color.Black
)

val DarkColorSchemeTerracotta = darkColorScheme(
    primary = Color(0xFFFB923C),
    onPrimary = Color.Black,
    background = Color(0xFF0A0A0A),
    onBackground = Color.White,
    surface = Color(0xFF1A1A1A),
    onSurface = Color.White,
    error = Color(0xFFF87171),
    onError = Color.Black
)

val DarkColorSchemeBerry = darkColorScheme(
    primary = Color(0xFFF472B6),
    onPrimary = Color.Black,
    background = Color(0xFF0A0A0A),
    onBackground = Color.White,
    surface = Color(0xFF1A1A1A),
    onSurface = Color.White,
    error = Color(0xFFF87171),
    onError = Color.Black
)

enum class ColorTheme(val displayName: String) {
    DEFAULT("По умолчанию"),
    MINT("Мятная"),
    LAVENDER("Лавандовая"),
    OCEAN("Океан"),
    TERRACOTTA("Терракота"),
    BERRY("Ягодная")
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
        !darkTheme && selectedColorTheme == ColorTheme.DEFAULT -> LightColorSchemeDefault
        !darkTheme && selectedColorTheme == ColorTheme.MINT -> LightColorSchemeMint
        !darkTheme && selectedColorTheme == ColorTheme.LAVENDER -> LightColorSchemeLavender
        !darkTheme && selectedColorTheme == ColorTheme.OCEAN -> LightColorSchemeOcean
        !darkTheme && selectedColorTheme == ColorTheme.TERRACOTTA -> LightColorSchemeTerracotta
        !darkTheme && selectedColorTheme == ColorTheme.BERRY -> LightColorSchemeBerry
        else -> LightColorSchemeDefault
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}