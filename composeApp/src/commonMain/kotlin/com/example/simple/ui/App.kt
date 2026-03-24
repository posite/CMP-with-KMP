package com.example.simple.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.simple.data.MealDto
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel = koinViewModel<MealViewModel>()
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        viewModel.getMeals()
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
                    items(viewModel.meals.value) {
                        MealItem(it)
                    }
                }
            }
        }
    }
}

@Composable
fun MealItem(item: MealDto) {
    Column(
        modifier = Modifier.fillMaxWidth().height(120.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.size(100.dp),
                model = item.strMealThumb,
                contentDescription = null,
            )

            Spacer(Modifier.size(16.dp))
            Column {
                Text(text = item.strMeal, fontSize = 18.sp)
                Text(text = item.strCategory, fontSize = 14.sp)
                Text(text = item.strYoutube, fontSize = 14.sp, maxLines = 1)
            }
        }
    }
}