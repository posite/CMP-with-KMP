package com.posite.simplekmpproject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.posite.simplekmpproject.Res
import com.posite.simplekmpproject.ios_24px
import org.jetbrains.compose.resources.painterResource


@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val painter = painterResource(Res.drawable.ios_24px)


//                    if(greeting.contains("Android")) {
//                        Image(
//                            imageVector = Icons.Default.Android,
//                            contentDescription = "Android Logo"
//                        )
//                    } else {
//                        Image(
//                            painter = painterResource(Res.drawable.ios_24px),
//                            contentDescription = "IOS Logo"
//                        )
//                    }
                    Text("r : ${Res.drawable.ios_24px}")
                    Text("Compose: $greeting")
                }
            }
        }
    }
}
