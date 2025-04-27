package com.example.buyride.data

data class Bike(
    val id: Int,
    val name: String,
    val type: String,
    val price: Int,
    val imageUrl: String,
    val topSeller: Boolean
)