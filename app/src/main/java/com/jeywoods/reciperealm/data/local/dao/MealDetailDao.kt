package com.jeywoods.reciperealm.data.local.dao

import androidx.room.*
import com.jeywoods.reciperealm.data.local.entities.MealDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDetailDao {

    @Query("SELECT * FROM meal_detail WHERE idMeal = :mealId")
    fun getMealDetail(mealId: String): Flow<MealDetailEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealDetail(mealDetail: MealDetailEntity)
    @Query("SELECT * FROM meal_detail ORDER BY RANDOM() LIMIT 1")
    fun getAnyMealDetail(): Flow<MealDetailEntity?>
}