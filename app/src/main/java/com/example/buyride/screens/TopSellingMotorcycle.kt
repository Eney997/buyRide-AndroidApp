package com.example.buyride.screens

import com.example.buyride.R

data class TopSellingMotorcycle
(
    val name: String,
    val type: String,
    val description: String,
    val imageRes: Int
)
{
    companion object {
        val topSellingBikes = listOf(
            TopSellingMotorcycle(
                name = "Name:Harley Davidson",
                type = "Type:Chopper",
                description = "Iconic American cruiser known for its comfort and long-distance capability.",
                imageRes = R.drawable.harly
            ),
            TopSellingMotorcycle(
                name = "Name:KTM 690 SMC R",
                type = "Type:SuperMoto",
                description = "High-performance SuperMoto built for urban agility and off-road fun.",
                imageRes = R.drawable.supermoto
            ),
            TopSellingMotorcycle(
                name = "Name:Triumph Street",
                type = "Type:Street",
                description = "Aggressive naked bike offering sharp handling and thrilling performance.",
                imageRes = R.drawable.triump
            ),
            TopSellingMotorcycle(
                name = "Name:MV AB 1000 RR",
                type = "Type:Naked",
                description = "Italian-engineered hyper naked motorcycle blending extreme power with sharp design.",
                imageRes = R.drawable.brutale
            )

        )
    }
}