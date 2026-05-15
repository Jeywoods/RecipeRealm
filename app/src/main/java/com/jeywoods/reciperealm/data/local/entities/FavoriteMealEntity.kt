package com.jeywoods.reciperealm.data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "favorite_meals",
    primaryKeys = ["mealId", "userId"]
)
data class FavoriteMealEntity(
    val mealId: String,
    val userId: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String?,
    val strArea: String?
)