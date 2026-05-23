package com.jeywoods.reciperealm.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.jeywoods.reciperealm.data.remote.MealDetailDto
import com.jeywoods.reciperealm.data.repository.MealRepository
import com.jeywoods.reciperealm.ui.components.AppTopBar
import com.jeywoods.reciperealm.ui.components.mealsDetailScreen.MealLabel
import com.jeywoods.reciperealm.ui.components.mealsDetailScreen.IngredientsSection
import com.jeywoods.reciperealm.ui.components.mealsDetailScreen.InstructionsSection
import com.jeywoods.reciperealm.ui.components.mealsDetailScreen.YouTubeSection
import com.jeywoods.reciperealm.ui.viewModel.FavoritesViewModel
import com.jeywoods.reciperealm.ui.viewModel.MealDetailViewModel
import org.koin.compose.getKoin

@Composable
fun MealDetailScreen(mealId: String, onBack: () -> Unit) {
    val repository = getKoin().get<MealRepository>()
    val favViewModel = getKoin().get<FavoritesViewModel>()
    val viewModel = remember(mealId) { MealDetailViewModel(repository, mealId) }

    val mealDetail by viewModel.meal.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isFavorite by favViewModel.isFavorite(mealId).collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AppTopBar(
            title = mealDetail?.strMeal?.take(30) ?: "Детали рецепта",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onBack,
            actions = {
                IconButton(
                    onClick = {
                        mealDetail?.let { meal ->
                            favViewModel.toggleFavorite(
                                mealId = meal.idMeal,
                                strMeal = meal.strMeal,
                                strMealThumb = meal.strMealThumb,
                                strCategory = meal.strCategory,
                                strArea = meal.strArea
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = "Favourites",
                        tint = if (isFavorite) Color.Red
                        else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )

        when {
            isLoading -> LoadingState()
            else -> {
                mealDetail?.let { meal ->
                    MealDetailContent(meal = meal)
                } ?: EmptyState()
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Uploading recipe...", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Рецепт не найден", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun MealDetailContent(meal: MealDetailDto, modifier: Modifier = Modifier) {
    val ingredients = remember(meal) { getIngredients(meal) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item { HeroImage(meal = meal) }
        item { TitleAndInfo(meal = meal) }
        if (ingredients.isNotEmpty()) {
            item { IngredientsSection(ingredients = ingredients) }
        }
        item { InstructionsSection(instructions = meal.strInstructions) }
        if (!meal.strYoutube.isNullOrBlank()) {
            item { YouTubeSection(youtubeUrl = meal.strYoutube) }
        }
    }
}

@Composable
private fun HeroImage(meal: MealDetailDto) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(meal.strMealThumb),
            contentDescription = meal.strMeal,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.6f)
                        ),
                        startY = 0.5f
                    )
                )
        )

        Text(
            text = meal.strMeal,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
        )
    }
}

@Composable
private fun TitleAndInfo(meal: MealDetailDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MealLabel(label = meal.strCategory)
            MealLabel(label = meal.strArea)
        }
    }
}

private fun getIngredients(meal: MealDetailDto): List<Pair<String, String>> {
    val ingredients = mutableListOf<Pair<String, String>>()
    for (i in 1..20) {
        val ingredient = getIngredientField(meal, i)
        val measure = getMeasureField(meal, i)
        if (!ingredient.isNullOrBlank() && ingredient != " ") {
            ingredients.add(ingredient.trim() to (measure?.trim() ?: ""))
        }
    }
    return ingredients
}

private fun getIngredientField(meal: MealDetailDto, index: Int): String? {
    return when (index) {
        1 -> meal.strIngredient1
        2 -> meal.strIngredient2
        3 -> meal.strIngredient3
        4 -> meal.strIngredient4
        5 -> meal.strIngredient5
        6 -> meal.strIngredient6
        7 -> meal.strIngredient7
        8 -> meal.strIngredient8
        9 -> meal.strIngredient9
        10 -> meal.strIngredient10
        11 -> meal.strIngredient11
        12 -> meal.strIngredient12
        13 -> meal.strIngredient13
        14 -> meal.strIngredient14
        15 -> meal.strIngredient15
        16 -> meal.strIngredient16
        17 -> meal.strIngredient17
        18 -> meal.strIngredient18
        19 -> meal.strIngredient19
        else -> meal.strIngredient20
    }
}

private fun getMeasureField(meal: MealDetailDto, index: Int): String? {
    return when (index) {
        1 -> meal.strMeasure1
        2 -> meal.strMeasure2
        3 -> meal.strMeasure3
        4 -> meal.strMeasure4
        5 -> meal.strMeasure5
        6 -> meal.strMeasure6
        7 -> meal.strMeasure7
        8 -> meal.strMeasure8
        9 -> meal.strMeasure9
        10 -> meal.strMeasure10
        11 -> meal.strMeasure11
        12 -> meal.strMeasure12
        13 -> meal.strMeasure13
        14 -> meal.strMeasure14
        15 -> meal.strMeasure15
        16 -> meal.strMeasure16
        17 -> meal.strMeasure17
        18 -> meal.strMeasure18
        19 -> meal.strMeasure19
        else -> meal.strMeasure20
    }
}