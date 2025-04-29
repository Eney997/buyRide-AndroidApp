package com.example.buyride.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
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
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.sp

@Composable
fun BikeScreen(paddingValues: PaddingValues, myNavController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModel: BikeViewModel = viewModel()
    val bikes by viewModel.bikes.collectAsState()
    var showLoading by remember { mutableStateOf(true) }
    var isConnected by remember { mutableStateOf(true) }

    // Function to check network
    fun refreshConnection() {
        isConnected = isNetworkAvailable(context)
        if (isConnected && bikes.isEmpty()) {
            viewModel.fetchBikes() //call if you want to re-fetch data
        }
    }

    LaunchedEffect(Unit) {
        isConnected = isNetworkAvailable(context)
    }

    LaunchedEffect(bikes) {
        if (bikes.isNotEmpty()) {
            delay(1000)
            showLoading = false
        }
    }

    when {
        !isConnected -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "No internet connection",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { refreshConnection() },
                        modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .border(
                            2.dp,
                            Color.Gray,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Retry", fontSize = 15.sp,color = Color.Gray)
                    }
                }
            }
        }
        showLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading...", color = Color.White, style = MaterialTheme.typography.titleMedium)
            }
        }
        else -> {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(bikes) { bike ->
                    PostCard(bike, myNavController)
                }
            }
        }
    }
}


@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@SuppressLint("ServiceCast")
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}
