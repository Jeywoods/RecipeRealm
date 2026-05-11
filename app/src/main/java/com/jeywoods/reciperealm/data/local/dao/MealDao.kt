package com.jeywoods.reciperealm.data.local.dao

import androidx.room.*
import com.jeywoods.reciperealm.data.local.entities.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM meals WHERE strCategory = :category")
    fun getMealsByCategory(category: String): Flow<List<MealEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(meals: List<MealEntity>)

}