package com.example.hudie.api

import com.example.hudie.models.DefaultResponse
import com.example.hudie.models.TokenResponse
import com.example.hudie.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface HudieApi {
    @GET("users")
    fun getUsers(): Call<ArrayList<UserResponse>>

    @GET("users/show")
    fun showUser(): Call<UserResponse>

    @POST("users/create")
    @FormUrlEncoded
    fun createUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("fullName") fullname: String
    ): Call<UserResponse>

    @PUT("users/update")
    @FormUrlEncoded
    fun updateUser(
        //TODO
    ): Call<DefaultResponse>

    @DELETE("users/delete")
    fun deleteUser(
        @Field("id") id: Int
    ): Call<DefaultResponse>

    @POST("users/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<TokenResponse>
}