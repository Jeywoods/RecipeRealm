package com.jeywoods.reciperealm.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jeywoods.reciperealm.ui.theme.ColorTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class AppSettingsManager(private val context: Context) {

    companion object {
        private val COLOR_THEME_KEY = stringPreferencesKey("color_theme")
        private val DARK_MODE_KEY = stringPreferencesKey("dark_mode")
    }

    val colorThemeFlow: Flow<ColorTheme> = context.dataStore.data
        .map { preferences ->
            val themeName = preferences[COLOR_THEME_KEY] ?: ColorTheme.DEFAULT.name
            try {
                ColorTheme.valueOf(themeName)
            } catch (e: Exception) {
                ColorTheme.DEFAULT
            }
        }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY]?.toBoolean() ?: false
        }

    suspend fun saveColorTheme(theme: ColorTheme) {
        context.dataStore.edit { preferences ->
            preferences[COLOR_THEME_KEY] = theme.name
        }
    }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled.toString()
        }
    }
}