package com.example.buyride.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.buyride.R

@Composable
fun PostCard(bike: Bike) {

    val context = LocalContext.current
    val cardColor = Color(ContextCompat.getColor(context, R.color.snackBarColor))
    val imageLoader = ImageLoader.Builder(context)
        .crossfade(true)
        .build()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Automatically add spacing
        ) {
            AsyncImage(
                model = bike.imageUrl,
                contentDescription = "${bike.name} Image", // Improved accessibility
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                imageLoader = imageLoader
            )

            Text(
                text = bike.name,
                color = Color.White, // Use theme color
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = bike.type,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Price: $${bike.price}",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )

        }
    }
}