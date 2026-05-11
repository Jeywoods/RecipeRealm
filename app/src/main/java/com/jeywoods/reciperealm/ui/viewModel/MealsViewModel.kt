package com.jeywoods.reciperealm.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeywoods.reciperealm.data.local.entities.MealEntity
import com.jeywoods.reciperealm.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MealsViewModel(
    private val repository: MealRepository,
    private val categoryName: String
) : ViewModel() {

    private val _meals = MutableStateFlow<List<MealEntity>>(emptyList())
    val meals: StateFlow<List<MealEntity>> = _meals.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isNetworkAvailable = MutableStateFlow(repository.isNetworkAvailable())
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    init {
        if (categoryName.isNotBlank()) {
            loadMealsByCategory(categoryName)
        }
    }

    fun loadMealsByCategory(categoryName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getMealsByCategory(categoryName)
                .catch { e ->
                    _error.value = "Ошибка загрузки блюд: ${e.message}"
                    _isLoading.value = false
                }
                .collect { mealsList ->
                    _meals.value = mealsList
                    _isLoading.value = false
                    _isNetworkAvailable.value = repository.isNetworkAvailable()
                }
        }
    }

    fun refresh() {
        loadMealsByCategory(categoryName)
    }
}