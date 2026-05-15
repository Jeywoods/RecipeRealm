package com.jeywoods.reciperealm.ui.navigation

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Search : Screens("search")
    object Favorites : Screens("favorites")
    object Profile : Screens("profile")

    object Categories : Screens("categories")
    object Meals : Screens("meals/{categoryName}") {
        fun passCategory(categoryName: String) = "meals/$categoryName"
    }
    object MealDetail : Screens("meal/{mealId}") {
        fun passMealId(mealId: String) = "meal/$mealId"
    }
    object Auth : Screens("auth")
    object Settings : Screens("settings")
}