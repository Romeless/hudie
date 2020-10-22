package com.example.hudie.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val id: Int,
    val username: String,
    @SerializedName("fullName") val full_name: String,
    val email: String,
    val password: String? = null,
    val salt: String? = null,
    val avatar: String? = null,
    val address: String? = null,
    @SerializedName("phoneNumber") val phone_number: String? = null,
    @SerializedName("joinDate")  val join_date: String? = null,
    @SerializedName("lastLogin")  val last_login: String? = null,
    val verified: Int,
    val admin: Int,
    val google: Int? = null
 )