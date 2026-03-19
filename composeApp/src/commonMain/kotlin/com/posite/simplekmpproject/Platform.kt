package com.posite.simplekmpproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform