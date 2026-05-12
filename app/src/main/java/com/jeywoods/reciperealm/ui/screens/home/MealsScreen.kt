package com.jeywoods.reciperealm.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeywoods.reciperealm.data.repository.MealRepository
import com.jeywoods.reciperealm.ui.components.AppTopBar
import com.jeywoods.reciperealm.ui.components.mealsScreen.MealCard
import com.jeywoods.reciperealm.ui.components.mealsScreen.MealEntityDisplay
import com.jeywoods.reciperealm.ui.viewModel.MealsViewModel
import org.koin.compose.getKoin

@Composable
fun MealsScreen(
    categoryName: String,
    onMealClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val repository = getKoin().get<MealRepository>()
    val viewModel: MealsViewModel = remember(categoryName) {
        MealsViewModel(repository, categoryName)
    }

    val meals by viewModel.meals.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppTopBar(
            title = categoryName,
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onBack
        )

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Ошибка: $error", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.refresh() }) {
                            Text("Повторить")
                        }
                    }
                }
            }
            meals.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Нет блюд в этой категории")
                }
            }
            else -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(meals) { meal ->
                        val mealDisplay = MealEntityDisplay(
                            idMeal = meal.idMeal,
                            strMeal = meal.strMeal,
                            strMealThumb = meal.strMealThumb
                        )
                        MealCard(
                            meal = mealDisplay,
                            onClick = { onMealClick(meal.idMeal) }
                        )
                    }
                }
            }
        }
    }
}