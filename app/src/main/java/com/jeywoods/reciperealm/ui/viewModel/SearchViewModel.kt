package com.jeywoods.reciperealm.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeywoods.reciperealm.data.remote.MealDto
import com.jeywoods.reciperealm.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")  //текущий поисковый запрос
    val query: StateFlow<String> = _query.asStateFlow()

    private val _results = MutableStateFlow<List<MealDto>>(emptyList())
    val results: StateFlow<List<MealDto>> = _results.asStateFlow()
    //индикатор загрузки
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery //сохраняем запрос
        if (newQuery.length >= 2) { //ищем только от 2 символов
            searchMeals(newQuery)
        } else if (newQuery.isEmpty()) {
            _results.value = emptyList()
        }
    }

    private fun searchMeals(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val meals = repository.searchMeals(query)
                _results.value = meals
            } catch (e: Exception) {
                _results.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}