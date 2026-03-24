package com.example.simple.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.jetbrains.compose.resources.getString

class MealService(private val client: HttpClient) {
    suspend fun getMeals() = client.get(getString(R.string.base_url))
}