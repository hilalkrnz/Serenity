package com.example.serenity.data.dto


import com.google.gson.annotations.SerializedName

data class AddToCartBody(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("productId")
    val productId: Int?
)