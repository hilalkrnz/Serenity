package com.example.serenity.data.dto

import com.google.gson.annotations.SerializedName

data class ClearAllProductBody(
    @SerializedName("userId")
    val userId: String?
)