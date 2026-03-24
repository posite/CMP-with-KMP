package com.example.simple.di

import org.koin.core.context.startKoin

fun initKoinIos() {
    startKoin {
        modules(commonModule)
    }
}