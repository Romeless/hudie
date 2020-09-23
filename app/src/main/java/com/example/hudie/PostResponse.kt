package com.example.hudie

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String
 )