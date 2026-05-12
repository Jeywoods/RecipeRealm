package com.jeywoods.reciperealm.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeywoods.reciperealm.data.repository.MealRepository
import com.jeywoods.reciperealm.ui.components.AppTopBar
import com.jeywoods.reciperealm.ui.components.mealsScreen.MealCard
import com.jeywoods.reciperealm.ui.components.mealsScreen.MealDtoDisplay
import com.jeywoods.reciperealm.ui.viewModel.SearchViewModel
import org.koin.compose.getKoin

@Composable
fun SearchScreen(
    onMealClick: (String) -> Unit
) {
    val repository = getKoin().get<MealRepository>()
    val viewModel = remember { SearchViewModel(repository) }

    val query by viewModel.query.collectAsState()
    val results by viewModel.results.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppTopBar(title = "Поиск рецептов")

        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Введите название блюда...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = MaterialTheme.shapes.large
        )

        when {
            isLoading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            results.isNotEmpty() -> LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(results, key = { it.idMeal }) { meal ->
                    MealCard(
                        meal = MealDtoDisplay(
                            idMeal = meal.idMeal,
                            strMeal = meal.strMeal,
                            strMealThumb = meal.strMealThumb
                        ),
                        onClick = { onMealClick(meal.idMeal) }
                    )
                }
            }

            query.length >= 2 && !isLoading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Ничего не найдено",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            else -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Введите название блюда для поиска",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}