package com.example.simple.ui

import com.example.simple.data.MealDto
import com.example.simple.ui.base.UiEffect
import com.example.simple.ui.base.UiEvent
import com.example.simple.ui.base.UiState

class MealContract {
    sealed class MealEvent : UiEvent {
        data object GetMeals : MealEvent()
    }

    data class MealUiState(
        val meals: List<MealDto> = emptyList()
    ) : UiState

    sealed class MealEffect : UiEffect {

    }
}