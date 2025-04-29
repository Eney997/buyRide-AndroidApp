package com.example.buyride.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.buyride.R

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val boxCol = Color(ContextCompat.getColor(context, R.color.snackBarColor))
    val scroll = rememberScrollState()

    val motoObligationTitle = listOf(
        "Verify the Bike's Documents",
        "Inspect the Bike Physically",
        "Request a Test Ride",
        "Ask for Service History",
        "Check for Outstanding Loans",
    )

    val motoObligationDesc = listOf(
        "Always check the RC (Registration Certificate), insurance papers, and pollution certificate before purchasing.",
        "Ensure you check the bike’s condition, engine sound, tire wear, and brakes for any issues.",
        "Never buy without a test ride. Feel the handling, brakes, gear shift, and comfort.",
        "A well-maintained bike comes with service records. It helps you judge how well it’s been taken care of.",
        "Make sure the bike is not under any active loan or has pending traffic fines.",
    )

    //zip to store in lazy
    val motoObligations = motoObligationTitle.zip(motoObligationDesc)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(bottom = 110.dp, top = 50.dp)
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll))
        {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = boxCol),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.homescreenbike),
                    contentDescription = "rider",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                motoObligations.forEach { (title, description) ->
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(150.dp)
                            .background(color = boxCol, shape = RoundedCornerShape(10.dp))
                            .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(start = 10.dp,end = 10.dp)) {
                Text(
                    "TOP SELLING BIKES",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column {
                TopSellingMotorcycle.topSellingBikes.take(4).forEach { bike ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = boxCol)
                            .fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Image(
                                painter = painterResource(id = bike.imageRes),
                                contentDescription = bike.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(150.dp).clip(RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = bike.name, color = Color.White, style = MaterialTheme.typography.bodyLarge)
                                Text(text = bike.type, color = Color.White, style = MaterialTheme.typography.bodyLarge)
                                Text(text = bike.description, color = Color.White,style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}