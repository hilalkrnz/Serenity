package com.example.serenity.data.repository

import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.dto.BaseResponse
import com.example.serenity.data.dto.Product
import com.example.serenity.data.mapper.ProductUiDataListMapperImpl
import com.example.serenity.data.mapper.ProductUiDataMapperImpl
import com.example.serenity.data.source.remote.SerenityRemoteDataSource
import com.example.serenity.di.coroutine.IoDispatcher
import com.example.serenity.domain.model.ProductUiData
import com.example.serenity.domain.repository.SerenityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SerenityRepositoryImpl @Inject constructor(
    private val serenityRemoteDataSource: SerenityRemoteDataSource,
    private val productListUiDataMapper: ProductUiDataListMapperImpl,
    private val productUiDataMapper: ProductUiDataMapperImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SerenityRepository {

    override suspend fun getAllProduct(): Flow<NetworkResponseState<List<ProductUiData>>> {
        return flow {
            when (val response = serenityRemoteDataSource.getAllProduct()) {
                is NetworkResponseState.Error -> emit(NetworkResponseState.Error(response.errorMessage))
                is NetworkResponseState.Success -> emit(
                    NetworkResponseState.Success(
                        mapProductList(
                            response.result
                        )
                    )
                )

                is NetworkResponseState.Fail -> emit(NetworkResponseState.Fail(response.failMessage))
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProductById(productId: Int): NetworkResponseState<ProductUiData> =
        withContext(ioDispatcher) {
            when (val response = serenityRemoteDataSource.getProductById(productId)) {
                is NetworkResponseState.Error -> NetworkResponseState.Error(response.errorMessage)
                is NetworkResponseState.Success ->
                    NetworkResponseState.Success(
                        mapProduct(
                            response.result
                        )
                    )

                is NetworkResponseState.Fail -> NetworkResponseState.Fail(response.failMessage)
            }
        }

    override suspend fun getCategories(): Flow<NetworkResponseState<List<String>>> {
        return flow {
            when (val response = serenityRemoteDataSource.getCategories()) {
                is NetworkResponseState.Error -> emit(NetworkResponseState.Error(response.errorMessage))
                is NetworkResponseState.Success -> emit(NetworkResponseState.Success(response.result))
                is NetworkResponseState.Fail -> emit(NetworkResponseState.Fail(response.failMessage))
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProductsByCategory(category: String): Flow<NetworkResponseState<List<ProductUiData>>> {
        return flow {
            when (val response = serenityRemoteDataSource.getProductsByCategory(category)) {
                is NetworkResponseState.Error -> emit(NetworkResponseState.Error(response.errorMessage))
                is NetworkResponseState.Success -> emit(
                    NetworkResponseState.Success(
                        mapProductList(
                            response.result
                        )
                    )
                )

                is NetworkResponseState.Fail -> emit(NetworkResponseState.Fail(response.failMessage))
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun addProductToCart(
        userId: String,
        productId: Int
    ): NetworkResponseState<BaseResponse> =
        withContext(ioDispatcher) {
            when (val response = serenityRemoteDataSource.addProductToCart(userId, productId)) {
                is NetworkResponseState.Error -> NetworkResponseState.Error(response.errorMessage)
                is NetworkResponseState.Success -> NetworkResponseState.Success(response.result)
                is NetworkResponseState.Fail -> NetworkResponseState.Fail(response.failMessage)
            }
        }


    override suspend fun deleteProductFromCart(userId: String, productId: Int): NetworkResponseState<BaseResponse> =
        withContext(ioDispatcher) {
            when (val response = serenityRemoteDataSource.deleteProductFromCart(userId, productId)) {
                is NetworkResponseState.Error -> NetworkResponseState.Error(response.errorMessage)
                is NetworkResponseState.Success -> NetworkResponseState.Success(response.result)
                is NetworkResponseState.Fail -> NetworkResponseState.Fail(response.failMessage)
            }
        }

    override suspend fun clearAllProductFromCart(userId: String): NetworkResponseState<BaseResponse> =
        withContext(ioDispatcher) {
            when (val response = serenityRemoteDataSource.clearAllProductFromCart(userId)) {
                is NetworkResponseState.Error -> NetworkResponseState.Error(response.errorMessage)
                is NetworkResponseState.Success -> NetworkResponseState.Success(response.result)
                is NetworkResponseState.Fail -> NetworkResponseState.Fail(response.failMessage)
            }
        }

    override suspend fun getProductsFromCart(userId: String): Flow<NetworkResponseState<List<ProductUiData>>> {
        return flow {
            when (val response = serenityRemoteDataSource.getProductsFromCart(userId)) {
                is NetworkResponseState.Error -> emit(NetworkResponseState.Error(response.errorMessage))
                is NetworkResponseState.Success -> emit(
                    NetworkResponseState.Success(
                        mapProductList(
                            response.result
                        )
                    )
                )

                is NetworkResponseState.Fail -> emit(NetworkResponseState.Fail(response.failMessage))
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun searchProduct(searchProduct: String): Flow<NetworkResponseState<List<ProductUiData>>> {
        return flow {
            when (val response = serenityRemoteDataSource.searchProduct(searchProduct)) {
                is NetworkResponseState.Error -> emit(NetworkResponseState.Error(response.errorMessage))
                is NetworkResponseState.Success -> emit(
                    NetworkResponseState.Success(
                        mapProductList(
                            response.result
                        )
                    )
                )

                is NetworkResponseState.Fail -> emit(NetworkResponseState.Fail(response.failMessage))
            }
        }.flowOn(ioDispatcher)
    }

    private fun mapProductList(mapProductList: List<Product>?): List<ProductUiData> {
        return productListUiDataMapper.map(mapProductList)
    }

    private fun mapProduct(mapProduct: Product?): ProductUiData {
        return productUiDataMapper.map(mapProduct)
    }
}