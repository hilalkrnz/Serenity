package com.example.serenity.data.source.remote

import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.dto.AddToCartBody
import com.example.serenity.data.dto.BaseResponse
import com.example.serenity.data.dto.CategoriesResponse
import com.example.serenity.data.dto.Product
import com.example.serenity.data.dto.ProductResponse

interface SerenityRemoteDataSource {
    suspend fun getAllProduct(): NetworkResponseState<List<Product>>
    suspend fun getProductById(productId: Int): NetworkResponseState<Product>
    suspend fun getCategories(): NetworkResponseState<List<String>>
    suspend fun getProductsByCategory(category: String): NetworkResponseState<List<Product>>
    suspend fun addProductToCart(userId: String, productId: Int): NetworkResponseState<BaseResponse>
    suspend fun deleteProductFromCart(userId: String, productId: Int): NetworkResponseState<BaseResponse>
    suspend fun clearAllProductFromCart(userId: String): NetworkResponseState<BaseResponse>
    suspend fun getProductsFromCart(userId: String): NetworkResponseState<List<Product>>
    suspend fun searchProduct(searchProduct: String): NetworkResponseState<List<Product>>
}