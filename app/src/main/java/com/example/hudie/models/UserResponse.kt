package com.example.hudie.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val id: Int,
    val username: String,
    @SerializedName("fullName") val full_name: String,
    val email: String,
    val password: String?,
    val salt: String?,
    val avatar: String?,
    val address: String?,
    @SerializedName("phoneNumber") val phone_number: String?,
    @SerializedName("joinDate")  val join_date: String?,
    @SerializedName("lastLogin")  val last_login: String?,
    val verified: Int,
    val admin: Int,
    val google: Int?
 )