package com.custom.hudie.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Base64
import okhttp3.OkHttpClient

object RetrofitClient {

    private val AUTH = "Basic " + Base64.encodeToString("username:password".toByteArray(), Base64.NO_WRAP)
    private const val BASE_URL = "https://hudie-api.herokuapp.com/todo/api/0.1/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance : HudieApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(HudieApi::class.java)
    }

}