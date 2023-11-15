package com.example.serenity.data.dto


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("salePrice")
    val salePrice: Double?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("imageOne")
    val imageOne: String?,
    @SerializedName("imageTwo")
    val imageTwo: String?,
    @SerializedName("imageThree")
    val imageThree: String?,
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("saleState")
    val saleState: Boolean?
)