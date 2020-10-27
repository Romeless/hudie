package com.example.hudie.models

import retrofit2.http.GET

data class DesignDetails(
    val size: String,
    val warna: String,
    val bahan: String,
    val kepala: String,
    val tangan: String,
    val badan: String
)