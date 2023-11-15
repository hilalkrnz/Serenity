package com.example.serenity.domain.repository

import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.database.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteProductRepository {
    suspend fun addToFavorite(product: FavoriteProductEntity)
    fun getFavoriteProducts(): Flow<NetworkResponseState<List<FavoriteProductEntity>>>
    suspend fun checkFavoriteProduct(id: Int): Int
    suspend fun removeFromFavorite(id: Int): Int
}