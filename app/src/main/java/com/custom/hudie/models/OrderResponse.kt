package com.custom.hudie.models

data class OrderResponse(
    val id: Int,
    val userID: Int? = 0,
    val designID: Int? = 0,
    val username: String,
    val address: String,
    val email: String,
    val qty: Int,
    val price: Int,
    val status: Int,
    val phoneNumber: String? = "",
    val details: String? = null,
    val images: String? = null,
    val imagesPosition: String? = null,
    val information: String? = ""
)