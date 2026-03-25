package com.example.simple.data

import com.example.simple.util.DataResult
import com.example.simple.util.handleKtorApi

interface MealRepository {
    suspend fun getMeals(): DataResult<MealResponse>
}

class MealRepositoryImpl(private val service: MealService) : MealRepository {
    override suspend fun getMeals(): DataResult<MealResponse> {
        return handleKtorApi({
            runCatching {
                service.getMeals()
            }
        }) { it }
    }

}