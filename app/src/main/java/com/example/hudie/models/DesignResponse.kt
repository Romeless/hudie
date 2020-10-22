package com.example.hudie.models

import com.google.gson.annotations.SerializedName

data class DesignResponse(
    val id: Int,
    @SerializedName("userID") val user_id: Int?,
    @SerializedName("designName") val design_name: String,
    val details: String?,
    val images: String?,
    val information: String?,
    val share: Int,
    val price: Int,
    @SerializedName("createDate") val create_date: String?,
    @SerializedName("updateDate")  val update_date: String?
)