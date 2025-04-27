package com.example.buyride.data

import retrofit2.http.GET

interface BikeApi {
    @GET("bikes")
    suspend fun getAllBikes(): List<Bike>
}