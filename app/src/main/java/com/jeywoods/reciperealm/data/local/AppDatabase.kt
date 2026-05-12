package com.jeywoods.reciperealm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jeywoods.reciperealm.data.local.dao.CategoryDao
import com.jeywoods.reciperealm.data.local.dao.MealDao
import com.jeywoods.reciperealm.data.local.dao.MealDetailDao
import com.jeywoods.reciperealm.data.local.entities.CategoryEntity
import com.jeywoods.reciperealm.data.local.entities.MealDetailEntity
import com.jeywoods.reciperealm.data.local.entities.MealEntity

@Database(
    entities = [
        CategoryEntity::class,
        MealEntity::class,
        MealDetailEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun mealDao(): MealDao
    abstract fun mealDetailDao(): MealDetailDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_realm_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}