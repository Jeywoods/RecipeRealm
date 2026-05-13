package com.jeywoods.reciperealm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMealEntity(
    @PrimaryKey val mealId: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String?,
    val strArea: String?
)