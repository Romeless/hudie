package com.example.hudie.api

import com.example.hudie.models.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface HudieApi {
    @GET("users")
    fun getUsers(): Call<ArrayList<UserResponse>>

    @GET("users/show/{id}")
    fun showUser(): Call<UserResponse>

    @POST("users/create")
    @FormUrlEncoded
    fun createUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("fullName") fullname: String,
        @Field("avatar") avatar: String?,
        @Field("address") address: String?,
        @Field("phoneNumber") phoneNumber: String?
    ): Call<UserResponse>

    @POST("users/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    )

    @POST("users/googleAuth")
    @FormUrlEncoded
    fun googleAuth(
        @Field("tokenID") tokenID: String,
        @Field("fullName") fullName: String,
        @Field("googleID") googleID: String,
        @Field("email") email: String,
        @Field("device") device: String?
    )
}