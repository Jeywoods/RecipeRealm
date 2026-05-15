package com.jeywoods.reciperealm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.jeywoods.reciperealm.di.appModule
import com.jeywoods.reciperealm.ui.navigation.NavGraph
import com.jeywoods.reciperealm.ui.theme.ColorTheme
import com.jeywoods.reciperealm.ui.theme.RecipeRealmTheme
import com.jeywoods.reciperealm.utils.AppSettingsManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

class MainActivity : ComponentActivity() {

    private lateinit var appSettingsManager: AppSettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        appSettingsManager = getKoin().get()

        setContent {
            var selectedColorTheme by remember { mutableStateOf<ColorTheme?>(null) }
            var isDarkMode by remember { mutableStateOf<Boolean?>(null) }

            LaunchedEffect(Unit) {
                selectedColorTheme = appSettingsManager.colorThemeFlow.first()
                isDarkMode = appSettingsManager.darkModeFlow.first()
            }

            if (selectedColorTheme != null && isDarkMode != null) {
                RecipeRealmTheme(
                    selectedColorTheme = selectedColorTheme!!,
                    darkTheme = isDarkMode!!
                ) {
                    NavGraph(
                        selectedColorTheme = selectedColorTheme!!,
                        isDarkMode = isDarkMode!!,
                        onColorThemeChanged = { theme ->
                            selectedColorTheme = theme
                            lifecycleScope.launch { appSettingsManager.saveColorTheme(theme) }
                        },
                        onDarkModeChanged = { darkMode ->
                            isDarkMode = darkMode
                            lifecycleScope.launch { appSettingsManager.saveDarkMode(darkMode) }
                        }
                    )
                }
            }
        }
    }
}