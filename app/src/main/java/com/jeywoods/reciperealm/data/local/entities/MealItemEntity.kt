package com.jeywoods.reciperealm.data.local.entities

import com.jeywoods.reciperealm.ui.components.mealsScreen.MealItem

data class MealItemEntity(
    override val idMeal: String,
    override val strMeal: String,
    override val strMealThumb: String
) : MealItem