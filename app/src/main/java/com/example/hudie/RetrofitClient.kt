package com.example.hudie

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://localhost/"


    val instance : HudieApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HudieApi::class.java)
    }

}