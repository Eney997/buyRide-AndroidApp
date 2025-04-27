package com.example.buyride.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

@Composable
fun BikeScreen(paddingValues: PaddingValues) {
    val viewModel: BikeViewModel = viewModel()
    val bikes by viewModel.bikes.collectAsState()
    var showLoading by remember { mutableStateOf(true) }

    LaunchedEffect(bikes) {
        if (bikes.isNotEmpty()) {
            delay(1000) // wait 1 second before hiding loading
            showLoading = false
        }
    }

    if (showLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", color = Color.White, style = MaterialTheme.typography.titleMedium)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(bikes) { bike ->
                PostCard(bike)
            }
        }
    }
}
