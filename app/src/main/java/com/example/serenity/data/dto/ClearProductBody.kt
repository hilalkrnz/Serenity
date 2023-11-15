package com.example.serenity.data.dto

import com.google.gson.annotations.SerializedName

data class ClearProductBody (
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("id")
    val productId: Int?
)