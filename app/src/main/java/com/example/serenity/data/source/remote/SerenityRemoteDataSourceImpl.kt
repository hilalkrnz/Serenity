package com.example.serenity.data.source.remote

import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.dto.AddToCartBody
import com.example.serenity.data.dto.BaseResponse
import com.example.serenity.data.dto.ClearAllProductBody
import com.example.serenity.data.dto.ClearProductBody
import com.example.serenity.data.dto.Product
import com.example.serenity.data.network.SerenityService
import javax.inject.Inject

class SerenityRemoteDataSourceImpl @Inject constructor(private val serenityService: SerenityService) :
    SerenityRemoteDataSource {

    override suspend fun getAllProduct(): NetworkResponseState<List<Product>> =
        try {
            val response = serenityService.getAllProduct().body()
            if (response?.status == 200) {
                NetworkResponseState.Success(response.products.orEmpty())
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }

    override suspend fun getProductById(productId: Int): NetworkResponseState<Product> =
        try {
            val response = serenityService.getProductById(productId).body()
            if (response?.status == 200 && response.product != null) {
                NetworkResponseState.Success(response.product)
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }

    override suspend fun getCategories(): NetworkResponseState<List<String>> =
        try {
            val response = serenityService.getCategories().body()
            if (response?.status == 200) {
                NetworkResponseState.Success(response.categories.orEmpty())
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }

    override suspend fun getProductsByCategory(category: String): NetworkResponseState<List<Product>> =
        try {
            val response = serenityService.getProductsByCategory(category).body()
            if (response?.status == 200) {
                NetworkResponseState.Success(response.products.orEmpty())
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }


    override suspend fun addProductToCart(
        userId: String,
        productId: Int
    ): NetworkResponseState<BaseResponse> =
        try {
            val addToCartBody = AddToCartBody(userId, productId)
            val response = serenityService.addProductToCart(addToCartBody).body()
            if (response?.status == 200) {
                NetworkResponseState.Success(response)
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }

    override suspend fun deleteProductFromCart(userId: String, productId: Int): NetworkResponseState<BaseResponse> =
        try {
            val body = ClearProductBody(userId, productId)
            val response = serenityService.deleteProductFromCart(body).body()
            if (response?.status == 200) {
                NetworkResponseState.Success(response)
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }

    override suspend fun clearAllProductFromCart(userId: String): NetworkResponseState<BaseResponse> =
        try {
            val body = ClearAllProductBody(userId)
            val response = serenityService.clearAllProductFromCart(body).body()
            if (response?.status == 200) {
                NetworkResponseState.Success(response)
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }

    override suspend fun getProductsFromCart(userId: String): NetworkResponseState<List<Product>> =
        try {
            val response = serenityService.getProductsFromCart(userId).body()
            if (response?.status == 200) {
                NetworkResponseState.Success(response.products.orEmpty())
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }

    override suspend fun searchProduct(searchProduct: String): NetworkResponseState<List<Product>> =
        try {
            val response = serenityService.searchProduct(searchProduct).body()
            if (response?.status == 200) {
                NetworkResponseState.Success(response.products.orEmpty())
            } else {
                NetworkResponseState.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }
}