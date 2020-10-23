package com.example.hudie.api

import com.example.hudie.models.DesignResponse
import com.example.hudie.models.TokenResponse
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
        @Field("avatar") avatar: String? = null,
        @Field("address") address: String? = null,
        @Field("phoneNumber") phone_number: String? = null
    ): Call<UserResponse>

    @POST("users/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("device") device: String? = null
    ): Call<TokenResponse>

    @POST("users/googleAuth")
    @FormUrlEncoded
    fun googleAuth(
        @Field("tokenID") token_ID: String,
        @Field("fullName") full_name: String,
        @Field("googleID") google_ID: String,
        @Field("email") email: String,
        @Field("device") device: String? = null
    ): Call<UserResponse>

    // ********************************************
    // DESIGN
    // ********************************************

    @GET("designs")
    fun getDesigns(): Call<ArrayList<DesignResponse>>

    @POST("designs/create")
    @FormUrlEncoded
    fun createDesign(
        @Field("userId") user_id: Int,
        @Field("designName") design_name: String,
        @Field("details") details: String,
        @Field("images") images: String? = null,
        @Field("imagesPosition") images_position: String? = null,
        @Field("information") information: String? = null,
        @Field("price") price: Int,
        @Field("share") share: Int
    ): Call<DesignResponse>
}