package com.example.serenity.domain.model

data class ProductUiData(
    val id: Int?,
    val title: String,
    val price: String?,
    val salePrice: String?,
    val description: String,
    val category: String,
    val imageOne: String,
    val rate: Double?,
    val saleState: Boolean?
)
