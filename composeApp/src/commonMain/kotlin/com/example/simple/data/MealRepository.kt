package com.example.simple.data

import com.example.simple.util.DataResult
import com.example.simple.util.handleKtorApi
import io.ktor.client.call.body

interface MealRepository {
    suspend fun getMeals() : DataResult<MealResponse>
}

class MealRepositoryImpl(private val service: MealService) : MealRepository {
    override suspend fun getMeals(): DataResult<MealResponse> {
        return handleKtorApi({
            runCatching {
                service.getMeals().body<MealResponse>()
            }
        }) { it }
    }

}