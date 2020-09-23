package com.example.hudie

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "10.0.2.2"


    val instance : HudieApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HudieApi::class.java)
    }

}