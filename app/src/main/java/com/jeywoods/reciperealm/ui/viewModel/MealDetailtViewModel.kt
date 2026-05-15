package com.jeywoods.reciperealm.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeywoods.reciperealm.data.remote.MealDetailDto
import com.jeywoods.reciperealm.data.repository.MealRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MealDetailViewModel(
    private val repository: MealRepository,
    private val mealId: String
) : ViewModel() {

    private val _mealDetail = MutableStateFlow<MealDetailDto?>(null)
    val mealDetail: StateFlow<MealDetailDto?> = _mealDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadMealDetail()
    }

    fun loadMealDetail() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val meal = repository.getMealDetails(mealId)
                _mealDetail.value = meal
            } catch (e: Exception) {
                _error.value = "Load error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}