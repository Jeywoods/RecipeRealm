package com.jeywoods.reciperealm.ui.screens.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jeywoods.reciperealm.data.local.entities.FavoriteMealEntity
import com.jeywoods.reciperealm.ui.components.AppTopBar
import com.jeywoods.reciperealm.ui.viewModel.FavoritesViewModel
import org.koin.compose.getKoin

@Composable
fun FavoritesScreen(onMealClick: (String) -> Unit) {
    val viewModel = getKoin().get<FavoritesViewModel>()
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(topBar = { AppTopBar(title = "My dishes") })
    { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Your saved recipes will appear here.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(8.dp))
                    Text("Click ❤️ on any recipe",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites, key = { it.mealId }) { meal ->
                    FavoriteCard(
                        meal = meal,
                        onClick = { onMealClick(meal.mealId) },
                        onRemove = {
                            viewModel.toggleFavorite(
                                mealId = meal.mealId,
                                strMeal = meal.strMeal,
                                strMealThumb = meal.strMealThumb,
                                strCategory = meal.strCategory,
                                strArea = meal.strArea
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteCard(
    meal: FavoriteMealEntity,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
            ) {
                Text(meal.strMeal, fontWeight = FontWeight.SemiBold, maxLines = 2)
                if (!meal.strCategory.isNullOrBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "${meal.strCategory} • ${meal.strArea ?: ""}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Favorite, "Delete", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}