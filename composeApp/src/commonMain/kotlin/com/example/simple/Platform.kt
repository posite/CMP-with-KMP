package com.example.simple

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform