package com.jeywoods.reciperealm.data.local.dao

import androidx.room.*
import com.jeywoods.reciperealm.data.local.entities.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId")
    fun getAllFavorites(userId: String): Flow<List<FavoriteMealEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE mealId = :mealId AND userId = :userId)")
    fun isFavorite(mealId: String, userId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(meal: FavoriteMealEntity)

    @Query("DELETE FROM favorite_meals WHERE mealId = :mealId AND userId = :userId")
    suspend fun removeFavorite(mealId: String, userId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(meals: List<FavoriteMealEntity>)
}