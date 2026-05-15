package com.jeywoods.reciperealm.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jeywoods.reciperealm.data.local.dao.FavoriteMealDao
import com.jeywoods.reciperealm.data.local.entities.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class FavoritesRepository(
    private val dao: FavoriteMealDao,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val uid get() = auth.currentUser?.uid ?: error("Пользователь не авторизован")

    private fun userFavRef() = firestore.collection("users").document(uid).collection("favorites")

    fun getAllFavorites(): Flow<List<FavoriteMealEntity>> = dao.getAllFavorites(uid)

    fun isFavorite(mealId: String): Flow<Boolean> = dao.isFavorite(mealId, uid)

    suspend fun addFavorite(meal: FavoriteMealEntity) {
        val entity = meal.copy(userId = uid)
        dao.addFavorite(entity)
        userFavRef().document(meal.mealId).set(entity.toMap()).await()
    }

    suspend fun removeFavorite(mealId: String) {
        dao.removeFavorite(mealId, uid)
        userFavRef().document(mealId).delete().await()
    }

    suspend fun syncFromFirestore() {
        val snapshot = userFavRef().get().await()
        val remote = snapshot.documents.mapNotNull { doc ->
            runCatching {
                FavoriteMealEntity(
                    mealId       = doc.id,
                    userId       = uid,
                    strMeal      = doc.getString("strMeal") ?: return@mapNotNull null,
                    strMealThumb = doc.getString("strMealThumb") ?: return@mapNotNull null,
                    strCategory  = doc.getString("strCategory"),
                    strArea      = doc.getString("strArea")
                )
            }.getOrNull()
        }
        dao.insertAll(remote)
    }

    private fun FavoriteMealEntity.toMap() = mapOf(
        "mealId"       to mealId,
        "userId"       to userId,
        "strMeal"      to strMeal,
        "strMealThumb" to strMealThumb,
        "strCategory"  to strCategory,
        "strArea"      to strArea
    )
}