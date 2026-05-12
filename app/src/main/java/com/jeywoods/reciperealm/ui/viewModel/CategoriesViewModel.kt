package com.jeywoods.reciperealm.ui.viewModel

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
        android.util.Log.d("CategoriesVM", "init called")
        loadCategories()
    }

    fun loadCategories() {
        android.util.Log.d("CategoriesVM", "loadCategories called")
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getCategories()
                .catch { e ->
                    android.util.Log.e("CategoriesVM", "Error: ${e.message}")
                    _error.value = "Ошибка загрузки категорий: ${e.message}"
                    _isLoading.value = false
                }
                .collect { categoriesList ->
                    android.util.Log.d("CategoriesVM", "Received ${categoriesList.size} categories")
                    _categories.value = categoriesList
                    _isLoading.value = false
                    _isNetworkAvailable.value = repository.isNetworkAvailable()
                }
        }
    }

    fun loadRandomMeal(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val randomMeal = repository.getRandomMeal()
                randomMeal?.let {
                    onSuccess(it.idMeal)
                }
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.message}"
            }
        }
    }

    fun refresh() {
        loadCategories()
    }
}