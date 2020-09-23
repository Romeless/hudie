package com.example.hudie

import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.GET

interface HudieApi {
    @GET("posts")
    fun getUsers(): Call<ArrayList<PostResponse>>
}