package com.example.simple.di

import com.example.simple.data.MealRepository
import com.example.simple.data.MealRepositoryImpl
import com.example.simple.data.MealService
import com.example.simple.data.createHttpClient
import com.example.simple.ui.MealViewModel
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val commonModule = module {
    single<HttpClient> { createHttpClient(get()) }
    single<MealService> { MealService(get()) }
    single<MealRepository> { MealRepositoryImpl(get()) }
    viewModelOf(::MealViewModel)
}

expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration = {}) =
    startKoin {
        config()
        modules(commonModule, platformModule)
    }