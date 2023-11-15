package com.example.serenity.data.dto

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("message")
    val message: String? = null
)
