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
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {  //запускаем корутину, которая привязана к жизненному циклу ViewModel
            _isLoading.value = true   //включаем индикатор загрузки
            _error.value = null       //сбрасываем предыдущую ошибку

            repository.getCategories()  //получаем Flow из репозитория
                .catch { e ->           //ловим возможные ошибки в потоке
                    _error.value = "Category loading error: ${e.message}"
                    _isLoading.value = false  //выключаем загрузку при ошибке
                }
                .collect { categoriesList ->  //каждый раз когда приходят новые данные из БД
                    _categories.value = categoriesList   //обновляем UI
                    _isLoading.value = false             //выключаем загрузку
                    _isNetworkAvailable.value = repository.isNetworkAvailable()  //обновляем статус сети
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
                _error.value = "Error: ${e.message}"
            }
        }
    }

    fun refresh() {
        loadCategories()
    }
}