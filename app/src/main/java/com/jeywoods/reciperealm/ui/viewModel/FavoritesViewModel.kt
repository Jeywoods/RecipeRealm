package com.jeywoods.reciperealm.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeywoods.reciperealm.data.local.entities.FavoriteMealEntity
import com.jeywoods.reciperealm.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repo: FavoritesRepository) : ViewModel() {

    val favorites: StateFlow<List<FavoriteMealEntity>> = repo.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun isFavorite(mealId: String): StateFlow<Boolean> =
        repo.isFavorite(mealId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleFavorite(meal: FavoriteMealEntity) {
        viewModelScope.launch {
            if (favorites.value.any { it.mealId == meal.mealId }) {
                repo.removeFavorite(meal.mealId)
            } else {
                repo.addFavorite(meal)
            }
        }
    }
}