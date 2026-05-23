package com.jeywoods.reciperealm.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeywoods.reciperealm.data.repository.MealRepository
import com.jeywoods.reciperealm.ui.components.AppTopBar
import com.jeywoods.reciperealm.ui.components.RandomMealCard
import com.jeywoods.reciperealm.ui.components.categoryScreen.CategoryCard
import com.jeywoods.reciperealm.ui.viewModel.CategoriesViewModel
import org.koin.compose.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCategoryClick: (String) -> Unit,
    onRandomMealClick: (String) -> Unit
) {
    val repository = getKoin().get<MealRepository>()
    val viewModel = remember { CategoriesViewModel(repository) }

    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppTopBar(title = "RecipeRealm")
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                RandomMealCard(
                    viewModel = viewModel,
                    onRandomMealClick = onRandomMealClick
                )
            }

            item {
                Text(
                    text = "Category",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onCategoryClick(category.strCategory) }
                    )
                }
            }
        }
    }
}

