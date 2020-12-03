package com.custom.hudie.api

import com.custom.hudie.models.DesignResponse
import com.custom.hudie.models.OrderResponse
import com.custom.hudie.models.TokenResponse
import com.custom.hudie.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface HudieApi {
    @GET("users")
    fun getUsers(): Call<ArrayList<UserResponse>>

    @GET("users/show/{id}")
    fun showUser(@Path("id") id: Int): Call<UserResponse>

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

    @POST("users/update/{id}")
    @FormUrlEncoded
    fun updateUser(
        @Path("id") id: Int,
        @Field("username") username: String? = null,
        @Field("password") password: String? = null,
        @Field("email") email: String,
        @Field("fullName") fullname: String,
        @Field("avatar") avatar: String? = null,
        @Field("address") address: String,
        @Field("phoneNumber") phone_number: String? = null,
        @Field("lastLogin") lastLogin: String? = null,
        @Field("token") token: String? = null
    ): Call<UserResponse>

    @POST("users/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("device") device: String? = null
    ): Call<TokenResponse>

    @POST("users/googleAuthMobile")
    @FormUrlEncoded
    fun googleAuth(
        @Field("tokenID") token_ID: String,
        @Field("fullName") full_name: String,
        @Field("googleID") google_ID: String,
        @Field("email") email: String,
        @Field("device") device: String? = null
    ): Call<TokenResponse>


    // ********************************************
    // DESIGN
    // ********************************************

    @GET("designs")
    fun getDesigns(): Call<ArrayList<DesignResponse>>

    @GET("designs/share")
    fun getSharableDesigns(): Call<ArrayList<DesignResponse>>

    @POST("designs/create")
    @FormUrlEncoded
    fun createDesign(
        @Field("userID") user_id: Int,
        @Field("designName") design_name: String,
        @Field("details") details: String,
        @Field("images") images: String? = null,
        @Field("imagesPosition") images_position: String? = null,
        @Field("information") information: String? = null,
        @Field("price") price: Int,
        @Field("share") share: Int
    ): Call<DesignResponse>

    @GET("designs/user/{id}")
    fun userDesign(@Path("id") userID: Int): Call<ArrayList<DesignResponse>>

    // ********************************************
    // ORDER
    // ********************************************

    @GET("orders")
    fun getOrders(): Call<ArrayList<OrderResponse>>

    @POST("orders/create")
    @FormUrlEncoded
    fun createOrder(
        @Field("userID") userID: Int? = null,
        @Field("designID") designID: Int? = null,
        @Field("email") email: String,
        @Field("qty") qty: Int,
        @Field("address") address: String,
        @Field("information") information: String,
        @Field("price") price: Int,
        @Field("phoneNumber") phoneNumber: String? = null,
        @Field("details") details: String? = null,
        @Field("images") images: String? = null,
        @Field("imagesPosition") imagesPosition: String? = null
    ): Call<OrderResponse>

    @PUT("orders/update/{id}")
    @FormUrlEncoded
    fun updateOrder(
        @Path("id") orderID :Int,
        @Field("userID") userID: Int,
        @Field("designID") designID: Int? = null,
        @Field("qty") qty: Int? = null,
        @Field("address") address: String? = null,
        @Field("information") information: String? = null,
        @Field("price") price: Int? = null,
        @Field("email") email: String? = null,
        @Field("phoneNumber") phone_number: String? = null,
        @Field("details") details: String? = null,
        @Field("images") images: String? = null,
        @Field("imagesPosition") imagesPosition: String? = null,
        @Field("status") status: Int? = null,
        @Field("token") token: String? = null
    ): Call<OrderResponse>

    @GET("orders/user/{id}")
    fun userOrder(@Path("id") userID : Int) : Call<ArrayList<OrderResponse>>

    @GET("orders/show/{id}")
    fun showOrder(@Path("id") orderID: Int) : Call<OrderResponse>
    // ********************************************
    // TOKEN
    // ********************************************

    @POST("tokens/verify")
    @FormUrlEncoded
    fun verifyToken(
        @Field("token") token: String,
        @Field("email") email: String,
        @Field("google") google: Int? = 0
    ): Call<TokenResponse>

}