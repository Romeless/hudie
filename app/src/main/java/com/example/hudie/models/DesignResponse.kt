package com.example.hudie.models

import com.google.gson.annotations.SerializedName

data class DesignResponse(
    val id: Int,
    @SerializedName("userID") val user_id: Int? = null,
    @SerializedName("designName") val design_name: String,
    val details: String? = null,
    val images: String? = null,
    val imagesPosition: String? = null,
    val information: String? = null,
    val share: Int,
    val price: Int,
    @SerializedName("createDate") val create_date: String? = null,
    @SerializedName("updateDate")  val update_date: String? = null,
    val username: String? = null
)