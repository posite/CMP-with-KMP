package com.example.simple

import androidx.compose.ui.window.ComposeUIViewController
import com.example.simple.di.initKoin
import com.example.simple.ui.App

fun MainViewController() = ComposeUIViewController { App() }

fun initKoinIos() {
    initKoin { /* iOS 전용 설정이 있다면 추가 */ }
}