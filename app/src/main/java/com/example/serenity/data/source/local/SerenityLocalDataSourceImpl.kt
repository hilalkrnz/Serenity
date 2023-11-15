package com.example.serenity.data.source.local

import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.database.FavoriteProductDao
import com.example.serenity.data.database.FavoriteProductEntity
import javax.inject.Inject

class SerenityLocalDataSourceImpl @Inject constructor(
    private val favoriteProductDao: FavoriteProductDao
) : SerenityLocalDataSource{
    override suspend fun addToFavorite(product: FavoriteProductEntity) =
        favoriteProductDao.addToFavorite(product)

    override suspend fun getFavoriteProducts(): NetworkResponseState<List<FavoriteProductEntity>> =
        try {
            val response = favoriteProductDao.getFavoriteProducts()
            NetworkResponseState.Success(response)
        }catch (e: Exception) {
            NetworkResponseState.Error(e.message.orEmpty())
        }

    override suspend fun checkFavoriteProduct(id: Int): Int =
        favoriteProductDao.checkFavoriteProduct(id)

    override suspend fun removeFromFavorite(id: Int): Int =
        favoriteProductDao.removeFromFavorite(id)
}