package com.example.simple.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simple.data.MealDto
import com.example.simple.data.MealRepository
import com.example.simple.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class MealViewModel(private val repository: MealRepository): ViewModel() {
    private val _meals : MutableStateFlow<List<MealDto>> = MutableStateFlow(emptyList())
    val meals : StateFlow<List<MealDto>> = _meals

    fun getMeals(): List<MealDto> {
        viewModelScope.launch {
            repository.getMeals().onSuccess {
                _meals.value = it.meals
            }
        }
    }
}