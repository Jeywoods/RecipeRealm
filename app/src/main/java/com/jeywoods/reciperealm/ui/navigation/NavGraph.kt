package com.jeywoods.reciperealm.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jeywoods.reciperealm.ui.components.BottomNavigationBar
import com.jeywoods.reciperealm.ui.screens.*
import com.jeywoods.reciperealm.ui.screens.favorites.FavoritesScreen
import com.jeywoods.reciperealm.ui.screens.home.HomeScreen
import com.jeywoods.reciperealm.ui.screens.profile.ProfileScreen
import com.jeywoods.reciperealm.ui.screens.search.SearchScreen
import com.jeywoods.reciperealm.ui.theme.ColorTheme

@Composable
fun NavGraph(
    selectedColorTheme: ColorTheme,
    isDarkMode: Boolean,
    onColorThemeChanged: (ColorTheme) -> Unit,
    onDarkModeChanged: (Boolean) -> Unit,
    startDestination: String = Screens.Home.route
) {
    val navController = rememberNavController()

    var currentRoute by remember { mutableStateOf(startDestination) }

    DisposableEffect(navController) {
        val listener = androidx.navigation.NavController.OnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route ?: Screens.Home.route
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Screens.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(300)
                )
            }
        ) {
            composable(route = Screens.Home.route) {
                HomeScreen(
                    onCategoryClick = { categoryName ->
                        navController.navigate(Screens.Meals.passCategory(categoryName))
                    },
                    onRandomMealClick = { mealId ->
                        navController.navigate(Screens.MealDetail.passMealId(mealId))
                    }
                )
            }

            composable(route = Screens.Search.route) {
                SearchScreen(
                    onMealClick = { mealId ->
                        navController.navigate(Screens.MealDetail.passMealId(mealId))
                    }
                )
            }

            composable(route = Screens.Favorites.route) {
                FavoritesScreen()
            }

            composable(route = Screens.Profile.route) {
                ProfileScreen(
                    onSettingsClick = {
                        navController.navigate(Screens.Settings.route)
                    }
                )
            }

            composable(route = Screens.Categories.route) {
                CategoryScreen(
                    onCategoryClick = { categoryName ->
                        navController.navigate(Screens.Meals.passCategory(categoryName))
                    },
                    onSettingsClick = {
                        navController.navigate(Screens.Settings.route)
                    },
                    onRandomMealClick = { mealId ->
                        navController.navigate(Screens.MealDetail.passMealId(mealId))
                    }
                )
            }

            composable(
                route = Screens.Meals.route,
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                MealsScreen(
                    categoryName = categoryName,
                    onMealClick = { mealId ->
                        navController.navigate(Screens.MealDetail.passMealId(mealId))
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screens.MealDetail.route,
                arguments = listOf(navArgument("mealId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                MealDetailScreen(
                    mealId = mealId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(route = Screens.Settings.route) {
                SettingsScreen(
                    selectedColorTheme = selectedColorTheme,
                    isDarkMode = isDarkMode,
                    onColorThemeSelected = onColorThemeChanged,
                    onDarkModeToggle = onDarkModeChanged,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}