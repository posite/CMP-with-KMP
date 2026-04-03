package com.example.simple.ui

import androidx.lifecycle.viewModelScope
import com.example.simple.data.MealRepository
import com.example.simple.ui.base.BaseViewModel
import com.example.simple.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(private val repository: MealRepository) :
    BaseViewModel<MealContract.MealEvent, MealContract.MealUiState, MealContract.MealEffect>() {

    override fun createInitialState(): MealContract.MealUiState {
        return MealContract.MealUiState()
    }

    override fun handleEvent(event: MealContract.MealEvent) {
        when (event) {
            is MealContract.MealEvent.GetMeals -> {
                viewModelScope.launch(Dispatchers.Default) {
                    repository.getMeals().onSuccess {
                        setState { copy(meals = it.meals) }
                    }
                }
            }
        }
    }

    fun getMeals() = setEvent(MealContract.MealEvent.GetMeals)
}