package com.example.simple.data

import kotlinx.serialization.Serializable

@Serializable
data class MealResponse(
    val meals: List<MealDto>
)