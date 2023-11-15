package com.example.serenity.data.dto


import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<String>?,
) : BaseResponse()