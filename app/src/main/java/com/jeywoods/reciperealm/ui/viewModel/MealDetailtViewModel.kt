package com.jeywoods.reciperealm.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeywoods.reciperealm.data.remote.MealDetailDto
import com.jeywoods.reciperealm.data.repository.MealRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class MealDetailViewModel(
    private val repository: MealRepository,
    private val mealId: String
) : ViewModel() {

    private val _meal = MutableStateFlow<MealDetailDto?>(null)
    val meal: StateFlow<MealDetailDto?> = _meal.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadMealDetails()
    }

    fun loadMealDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = repository.getMealDetails(mealId)
                if (result != null) {
                    _meal.value = result
                } else {
                    _error.value = "Рецепт не найден"
                }
            } catch (e: SocketTimeoutException) {
                _error.value = "Сервер не отвечает"
                Log.e("MealDetailViewModel", "Timeout: ${e.message}")
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки: ${e.message}"
                Log.e("MealDetailViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}