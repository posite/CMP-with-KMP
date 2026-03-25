package com.example.simple.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.example.simple.data.MealDto
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel = koinViewModel<MealViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getMeals()
    }

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Scaffold(contentWindowInsets = WindowInsets.safeDrawing) { innerPadding ->
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize()
                    .padding(12.dp, innerPadding.calculateTopPadding()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    //val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(0.dp, 32.dp)
                    ) {
                        items(
                            items = viewModel.meals.value,
                            key = { it.idMeal },
                            contentType = { "meal" }) {
                            MealItem(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealItem(item: MealDto) {
    val uriHandler = LocalUriHandler.current
    val hasYoutubeLink = item.strYoutube.isNotBlank()
    Column(
        modifier = Modifier.fillMaxWidth().height(120.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(item.strMealThumb)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = item.strMeal,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게
                    .background(Color.LightGray), // 로딩 전 배경색 (플레이스홀더 역할)
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.size(16.dp))
            Column {
                Text(text = item.strMeal, fontSize = 18.sp)
                Text(text = item.strCategory, fontSize = 14.sp)
                Text(
                    text = item.strYoutube,
                    fontSize = 14.sp,
                    maxLines = 1,
                    modifier = Modifier.clickable(enabled = hasYoutubeLink) {
                        try {
                            uriHandler.openUri(item.strYoutube)
                        } catch (e: Exception) {

                        }
                    })
            }
        }
    }
}