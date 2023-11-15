package com.example.serenity.domain.repository

import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.dto.BaseResponse
import com.example.serenity.data.dto.Product
import com.example.serenity.domain.model.ProductUiData
import kotlinx.coroutines.flow.Flow

interface SerenityRepository {
    suspend fun getAllProduct(): Flow<NetworkResponseState<List<ProductUiData>>>
    suspend fun getProductById(productId: Int): NetworkResponseState<ProductUiData>
    suspend fun getCategories(): Flow<NetworkResponseState<List<String>>>
    suspend fun getProductsByCategory(category: String): Flow<NetworkResponseState<List<ProductUiData>>>
    suspend fun addProductToCart(userId: String, productId: Int): NetworkResponseState<BaseResponse>
    suspend fun deleteProductFromCart(userId: String, productId: Int): NetworkResponseState<BaseResponse>
    suspend fun clearAllProductFromCart(userId: String): NetworkResponseState<BaseResponse>
    suspend fun getProductsFromCart(userId: String): Flow<NetworkResponseState<List<ProductUiData>>>
    suspend fun searchProduct(searchProduct: String): Flow<NetworkResponseState<List<ProductUiData>>>
}