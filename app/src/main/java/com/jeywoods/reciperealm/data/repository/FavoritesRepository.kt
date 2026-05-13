package com.jeywoods.reciperealm.data.repository

import com.jeywoods.reciperealm.data.local.dao.FavoriteMealDao
import com.jeywoods.reciperealm.data.local.entities.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val dao: FavoriteMealDao) {
    fun getAllFavorites(): Flow<List<FavoriteMealEntity>> = dao.getAllFavorites()
    fun isFavorite(mealId: String): Flow<Boolean> = dao.isFavorite(mealId)
    suspend fun addFavorite(meal: FavoriteMealEntity) = dao.addFavorite(meal)
    suspend fun removeFavorite(mealId: String) = dao.removeFavorite(mealId)
}