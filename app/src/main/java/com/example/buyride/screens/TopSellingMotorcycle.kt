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
                name = "Street Bob",
                type = "Chopper",
                description = "Long forks and relaxed riding for cool cruising.",
                imageRes = R.drawable.choppa
            ),
            TopSellingMotorcycle(
                name = "MegaGas 700cc",
                type = "SuperMoto",
                description = "Lightweight urban beast with great handling.",
                imageRes = R.drawable.smoto
            ),
            TopSellingMotorcycle(
                name = "MT07",
                type = "Street",
                description = "Powerful and sleek for everyday road riding.",
                imageRes = R.drawable.street
            ),
            TopSellingMotorcycle(
                name = "Nova Racer X",
                type = "NeoCafeRacer",
                description = "Retro style with modern performance.",
                imageRes = R.drawable.neoracer
            )
        )
    }
}