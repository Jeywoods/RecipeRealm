package com.jeywoods.reciperealm.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jeywoods.reciperealm.data.local.entities.FavoriteMealEntity
import com.jeywoods.reciperealm.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repo: FavoritesRepository) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    init {
        if (auth.currentUser != null) {
            viewModelScope.launch { runCatching { repo.syncFromFirestore() } }
        }
    }

    val favorites: StateFlow<List<FavoriteMealEntity>> = repo.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun isFavorite(mealId: String): StateFlow<Boolean> =
        repo.isFavorite(mealId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleFavorite(
        mealId: String,
        strMeal: String,
        strMealThumb: String,
        strCategory: String? = null,
        strArea: String? = null
    ) {
        viewModelScope.launch {
            val alreadySaved = repo.isFavorite(mealId).first()
            if (alreadySaved) {
                repo.removeFavorite(mealId)
            } else {
                repo.addFavorite(
                    FavoriteMealEntity(
                        mealId = mealId,
                        userId = auth.currentUser?.uid ?: return@launch,
                        strMeal = strMeal,
                        strMealThumb = strMealThumb,
                        strCategory = strCategory,
                        strArea = strArea
                    )
                )
            }
        }
    }
}