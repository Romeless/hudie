package com.example.hudie.models

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("userID") val user_id: String,
    val token: String,
    val username: String,
    val device: String,
    @SerializedName("createDate") val create_date: String,
    @SerializedName("expireDate") val expire_date: String,
    val admin: Int? = 0
)