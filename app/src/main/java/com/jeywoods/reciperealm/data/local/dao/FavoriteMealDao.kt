package com.jeywoods.reciperealm.data.local.dao

import androidx.room.*
import com.jeywoods.reciperealm.data.local.entities.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {
    @Query("SELECT * FROM favorite_meals")
    fun getAllFavorites(): Flow<List<FavoriteMealEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE mealId = :mealId)")
    fun isFavorite(mealId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(meal: FavoriteMealEntity)

    @Query("DELETE FROM favorite_meals WHERE mealId = :mealId")
    suspend fun removeFavorite(mealId: String)
}