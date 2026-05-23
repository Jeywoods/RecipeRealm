package com.jeywoods.reciperealm.data.repository

import android.util.Log
import com.jeywoods.reciperealm.data.local.AppDatabase
import com.jeywoods.reciperealm.data.local.entities.CategoryEntity
import com.jeywoods.reciperealm.data.local.entities.MealDetailEntity
import com.jeywoods.reciperealm.data.local.entities.MealEntity
import com.jeywoods.reciperealm.data.remote.ApiService
import com.jeywoods.reciperealm.data.remote.MealDetailDto
import com.jeywoods.reciperealm.data.remote.MealDto
import com.jeywoods.reciperealm.utils.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class MealRepository(
    private val apiService: ApiService,
    private val networkManager: NetworkManager,
    private val database: AppDatabase
) {
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun getCategories(): Flow<List<CategoryEntity>> {
        repositoryScope.launch {
            try {
                refreshCategories()
            } catch (e: Exception) {
                Log.e("Repository", "Ошибка обновления категорий: ${e.message}")
            }
        }
        return database.categoryDao().getAllCategories()
    }

    fun getMealsByCategory(categoryName: String): Flow<List<MealEntity>> {
        repositoryScope.launch {
            try {
                refreshMealsByCategory(categoryName)
            } catch (e: Exception) {
                Log.e("Repository", "Ошибка обновления блюд: ${e.message}")
            }
        }
        return database.mealDao().getMealsByCategory(categoryName)


    }

    suspend fun getMealDetails(mealId: String): MealDetailDto? {
        //сначала проверяем кэш
        val cachedDetail = withContext(Dispatchers.IO) {
            database.mealDetailDao().getMealDetail(mealId).firstOrNull()
        }

        if (cachedDetail != null) {
            //есть в кэше - возвращаем сразу
            if (networkManager.isNetworkAvailable()) {
                //фоном обновляем
                repositoryScope.launch {
                    try {
                        fetchAndSaveMealDetail(mealId)
                    } catch (e: Exception) {
                        Log.e("MealRepository", "Background update failed: ${e.message}")
                    }
                }
            }
            return cachedDetail.toDto()
        }

        return try {
            fetchAndSaveMealDetail(mealId)
        } catch (e: SocketTimeoutException) {
            Log.e("MealRepository", "Timeout getting meal details: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("MealRepository", "Error getting meal details: ${e.message}")
            null
        }
    }

    private suspend fun fetchAndSaveMealDetail(mealId: String): MealDetailDto? {
        val response = apiService.getMealDetails(mealId)
        val detail = response.meals?.firstOrNull()
        detail?.let { saveMealToDatabase(it) }
        return detail
    }

    suspend fun getRandomMeal(): MealDetailDto? {
        return try {
            if (!networkManager.isNetworkAvailable()) {
                return database.mealDetailDao().getAnyMealDetail().firstOrNull()?.toDto()
            }

            val response = apiService.getRandomMeal()
            val meal = response.meals?.firstOrNull()

            if (meal != null) {
                saveMealToDatabase(meal)
            }

            meal
        } catch (e: SocketTimeoutException) {
            Log.e("MealRepository", "Timeout getting random meal: ${e.message}")
            database.mealDetailDao().getAnyMealDetail().firstOrNull()?.toDto()
        } catch (e: Exception) {
            Log.e("MealRepository", "Error getting random meal: ${e.message}")
            database.mealDetailDao().getAnyMealDetail().firstOrNull()?.toDto()
        }
    }

    fun isNetworkAvailable(): Boolean = networkManager.isNetworkAvailable()

    private suspend fun refreshCategories() {
        if (!networkManager.isNetworkAvailable()) return

        try {
            val response = apiService.getCategories()
            val categories = response.categories.map { category ->
                CategoryEntity(
                    idCategory = category.idCategory,
                    strCategory = category.strCategory,
                    strCategoryThumb = category.strCategoryThumb,
                    strCategoryDescription = category.strCategoryDescription
                )
            }
            withContext(Dispatchers.IO) {
                database.categoryDao().insertAll(categories)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun saveMealToDatabase(dto: MealDetailDto) {
        withContext(Dispatchers.IO) {
            Log.d("SAVE", "Сохраняем блюдо: ${dto.idMeal}")
            Log.d("SAVE", "strIngredient1 = ${dto.strIngredient1}")
            Log.d("SAVE", "strMeasure1 = ${dto.strMeasure1}")

            val entity = dto.toEntity()
            database.mealDetailDao().insertMealDetail(entity)

            Log.d("SAVE", "Блюдо сохранено в бд")
        }
    }

    private suspend fun refreshMealsByCategory(categoryName: String) {
        if (!networkManager.isNetworkAvailable()) return

        try {
            val response = apiService.getMealsByCategory(categoryName)
            val meals = response.meals?.map { meal ->
                MealEntity(
                    idMeal = meal.idMeal,
                    strMeal = meal.strMeal,
                    strMealThumb = meal.strMealThumb,
                    strCategory = categoryName,
                    strArea = "",
                    strInstructions = "",
                    strYoutube = null
                )
            } ?: emptyList()

            withContext(Dispatchers.IO) {
                database.mealDao().insertAll(meals)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun searchMeals(query: String): List<MealDto> {
        return try {
            val response = apiService.searchMeals(query)
            response.meals ?: emptyList()
        } catch (e: Exception) {
            Log.e("MealRepository", "Ошибка поиска: ${e.message}", e)
            emptyList()
        }
    }
}

private fun MealDetailDto.toEntity() = MealDetailEntity(
    idMeal = idMeal,
    strMeal = strMeal,
    strMealThumb = strMealThumb,
    strCategory = strCategory,
    strArea = strArea,
    strInstructions = strInstructions,
    strYoutube = strYoutube,
    strIngredient1 = strIngredient1,
    strIngredient2 = strIngredient2,
    strIngredient3 = strIngredient3,
    strIngredient4 = strIngredient4,
    strIngredient5 = strIngredient5,
    strIngredient6 = strIngredient6,
    strIngredient7 = strIngredient7,
    strIngredient8 = strIngredient8,
    strIngredient9 = strIngredient9,
    strIngredient10 = strIngredient10,
    strIngredient11 = strIngredient11,
    strIngredient12 = strIngredient12,
    strIngredient13 = strIngredient13,
    strIngredient14 = strIngredient14,
    strIngredient15 = strIngredient15,
    strIngredient16 = strIngredient16,
    strIngredient17 = strIngredient17,
    strIngredient18 = strIngredient18,
    strIngredient19 = strIngredient19,
    strIngredient20 = strIngredient20,
    strMeasure1 = strMeasure1,
    strMeasure2 = strMeasure2,
    strMeasure3 = strMeasure3,
    strMeasure4 = strMeasure4,
    strMeasure5 = strMeasure5,
    strMeasure6 = strMeasure6,
    strMeasure7 = strMeasure7,
    strMeasure8 = strMeasure8,
    strMeasure9 = strMeasure9,
    strMeasure10 = strMeasure10,
    strMeasure11 = strMeasure11,
    strMeasure12 = strMeasure12,
    strMeasure13 = strMeasure13,
    strMeasure14 = strMeasure14,
    strMeasure15 = strMeasure15,
    strMeasure16 = strMeasure16,
    strMeasure17 = strMeasure17,
    strMeasure18 = strMeasure18,
    strMeasure19 = strMeasure19,
    strMeasure20 = strMeasure20
)

private fun MealDetailEntity.toDto() = MealDetailDto(
    idMeal = idMeal,
    strMeal = strMeal,
    strMealThumb = strMealThumb,
    strCategory = strCategory,
    strArea = strArea,
    strInstructions = strInstructions,
    strYoutube = strYoutube,
    strIngredient1 = strIngredient1,
    strIngredient2 = strIngredient2,
    strIngredient3 = strIngredient3,
    strIngredient4 = strIngredient4,
    strIngredient5 = strIngredient5,
    strIngredient6 = strIngredient6,
    strIngredient7 = strIngredient7,
    strIngredient8 = strIngredient8,
    strIngredient9 = strIngredient9,
    strIngredient10 = strIngredient10,
    strIngredient11 = strIngredient11,
    strIngredient12 = strIngredient12,
    strIngredient13 = strIngredient13,
    strIngredient14 = strIngredient14,
    strIngredient15 = strIngredient15,
    strIngredient16 = strIngredient16,
    strIngredient17 = strIngredient17,
    strIngredient18 = strIngredient18,
    strIngredient19 = strIngredient19,
    strIngredient20 = strIngredient20,
    strMeasure1 = strMeasure1,
    strMeasure2 = strMeasure2,
    strMeasure3 = strMeasure3,
    strMeasure4 = strMeasure4,
    strMeasure5 = strMeasure5,
    strMeasure6 = strMeasure6,
    strMeasure7 = strMeasure7,
    strMeasure8 = strMeasure8,
    strMeasure9 = strMeasure9,
    strMeasure10 = strMeasure10,
    strMeasure11 = strMeasure11,
    strMeasure12 = strMeasure12,
    strMeasure13 = strMeasure13,
    strMeasure14 = strMeasure14,
    strMeasure15 = strMeasure15,
    strMeasure16 = strMeasure16,
    strMeasure17 = strMeasure17,
    strMeasure18 = strMeasure18,
    strMeasure19 = strMeasure19,
    strMeasure20 = strMeasure20
)

