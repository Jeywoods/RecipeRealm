package com.jeywoods.reciperealm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeywoods.reciperealm.data.local.entities.CategoryEntity
import com.jeywoods.reciperealm.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories: StateFlow<List<CategoryEntity>> = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isNetworkAvailable = MutableStateFlow(repository.isNetworkAvailable())
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getCategories()
                .catch { e ->
                    _error.value = "Ошибка загрузки категорий: ${e.message}"
                    _isLoading.value = false
                }
                .collect { categoriesList ->
                    _categories.value = categoriesList
                    _isLoading.value = false
                    _isNetworkAvailable.value = repository.isNetworkAvailable()
                }
        }
    }

    fun loadRandomMeal(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val randomMeal = repository.getRandomMeal()
                if (randomMeal != null) {
                    onSuccess(randomMeal.idMeal)
                } else {
                    _error.value = "Не удалось загрузить случайный рецепт"
                }
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        loadCategories()
    }
}