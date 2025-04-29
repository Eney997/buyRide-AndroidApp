package com.example.buyride.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun BuyMotoScreen(bikeName: String, bikePrice: String, bikeType: String, bikeImage: String, bikeInfo: String,myNavController: NavController) {

    val scroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopStart
    ) {
        Column(modifier = Modifier
            .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 120.dp)
            .verticalScroll(scroll))
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Black)
                    .shadow(0.dp, RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(bikeImage),
                    contentDescription = bikeName,
                    contentScale = ContentScale.Crop, // Use Crop for better fit
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // fixed height
                        .clip(RoundedCornerShape(10.dp)) // clip again for Image
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            Text("Bike Name: $bikeName",style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(5.dp))

            Text("Price: $$bikePrice",style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(5.dp))

            Text("Type: $bikeType",style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(5.dp))

            Text("Description: $bikeInfo",style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(50.dp)
                    .width(130.dp)
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                onClick = {
                    myNavController.navigate("ChargeScreen")
                }
            )
            {
                Text("BUY", fontSize = 20.sp, color = Color.Gray)
            }
        }
    }
}