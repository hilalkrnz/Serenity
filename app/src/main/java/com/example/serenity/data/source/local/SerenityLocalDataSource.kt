package com.example.serenity.data.source.local

import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.database.FavoriteProductEntity

interface SerenityLocalDataSource {
    suspend fun addToFavorite(product: FavoriteProductEntity)
    suspend fun getFavoriteProducts(): NetworkResponseState<List<FavoriteProductEntity>>
    suspend fun checkFavoriteProduct(id: Int): Int
    suspend fun removeFromFavorite(id: Int): Int
}