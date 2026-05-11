package com.jeywoods.reciperealm.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeywoods.reciperealm.ui.navigation.Screens

data class NavItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
)

val navItems = listOf(
    NavItem("Главная", Icons.Default.Home, Icons.Filled.Home, Screens.Home.route),
    NavItem("Поиск", Icons.Default.Search, Icons.Filled.Search, Screens.Search.route),
    NavItem("Мои блюда", Icons.Default.FavoriteBorder, Icons.Filled.Favorite, Screens.Favorites.route),
    NavItem("Профиль", Icons.Default.Person, Icons.Filled.Person, Screens.Profile.route)
)

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        if (currentRoute == item.route) item.selectedIcon else item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}