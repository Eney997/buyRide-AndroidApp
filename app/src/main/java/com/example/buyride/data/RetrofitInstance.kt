package com.example.buyride.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    val api: BikeApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/") // For emulator
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                okhttp3.OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS) // Set a timeout for connection
                    .readTimeout(15, TimeUnit.SECONDS) // Set a timeout for reading data
                    .build()
            )
            .build()
            .create(BikeApi::class.java)
    }
}
