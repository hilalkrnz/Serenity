package com.example.serenity.data.dto

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("product")
    val product: Product?,
) : BaseResponse()
