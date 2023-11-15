package com.example.serenity.data.repository

import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.data.source.local.SerenityLocalDataSource
import com.example.serenity.di.coroutine.IoDispatcher
import com.example.serenity.domain.repository.FavoriteProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteProductRepositoryImpl @Inject constructor(
    private val localDataSource: SerenityLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FavoriteProductRepository {
    override suspend fun addToFavorite(product: FavoriteProductEntity) =
        withContext(ioDispatcher) {
            localDataSource.addToFavorite(product)
        }

    override fun getFavoriteProducts(): Flow<NetworkResponseState<List<FavoriteProductEntity>>> =
        flow {
            when (val response = localDataSource.getFavoriteProducts()) {
                is NetworkResponseState.Error -> emit(NetworkResponseState.Error(response.errorMessage))
                is NetworkResponseState.Success -> emit(NetworkResponseState.Success(response.result))
                is NetworkResponseState.Fail -> emit(NetworkResponseState.Fail(response.failMessage))
            }
        }.flowOn(ioDispatcher)

    override suspend fun checkFavoriteProduct(id: Int): Int =
        withContext(ioDispatcher) {
            localDataSource.checkFavoriteProduct(id)
        }

    override suspend fun removeFromFavorite(id: Int): Int =
        withContext(ioDispatcher) {
            localDataSource.removeFromFavorite(id)
        }
}