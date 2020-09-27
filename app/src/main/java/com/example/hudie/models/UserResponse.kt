package com.example.hudie.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val id: Int,
    val username: String,
    @SerializedName("fullName") val fullname: String,
    val password: String,
    val role: String,
    val avatar: String,
    val email: String,
    val address: String,
    @SerializedName("phoneNumber") val phonenumber: String,
    val joinDate: String
 )