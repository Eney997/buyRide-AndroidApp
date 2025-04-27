package com.example.buyride.data

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.buyride.R

@Composable
fun PostCard(bike: Bike,myNavController: NavController) {

    val context = LocalContext.current
    val cardColor = Color(ContextCompat.getColor(context, R.color.snackBarColor))
    val imageLoader = ImageLoader.Builder(context)
        .crossfade(true)
        .build()
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = bike.imageUrl,
                    contentDescription = "${bike.name} Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    imageLoader = imageLoader
                )

                Text(
                    text = "Model Name: ${bike.name}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Type: ${bike.type}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Price: $${bike.price}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Icons in the bottom-right corner
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border),
                    contentDescription = if (isFavorite) "favorite" else "not favorite",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable {
                            // Toggle the state on click
                            isFavorite = !isFavorite
                        }
                )

                Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "share", tint = Color.White, modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable {
                        val encodedImage = Uri.encode(bike.imageUrl)
                        myNavController.navigate(
                            "BuyMotoScreen/${bike.name}/${bike.price}/${bike.type}/$encodedImage"
                        )
                    }
                )

            }
        }
    }

}