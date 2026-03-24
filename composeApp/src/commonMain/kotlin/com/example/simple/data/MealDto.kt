package com.example.simple.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    @SerialName("idMeal")
    val idMeal: String,
    @SerialName("strArea")
    val strArea: String,
    @SerialName("strCategory")
    val strCategory: String,
    @SerialName( "strInstructions")
    val strInstructions: String,
    @SerialName("strMeal")
    val strMeal: String,
    @SerialName("strMealThumb")
    val strMealThumb: String,
    @SerialName("strYoutube")
    val strYoutube: String
)