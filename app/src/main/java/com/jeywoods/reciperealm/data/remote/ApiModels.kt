package com.jeywoods.reciperealm.data.remote

import com.jeywoods.reciperealm.ui.components.mealsScreen.MealItem

data class CategoryResponse(
    val categories: List<CategoryDto>
)

data class CategoryDto(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)

data class CategoryMealsResponse(
    val meals: List<MealDto>?
)

data class MealDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)


data class MealItemDto(
    override val idMeal: String,
    override val strMeal: String,
    override val strMealThumb: String
) : MealItem