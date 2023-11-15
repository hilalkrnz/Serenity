package com.example.serenity.di.mapper

import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.data.dto.Product
import com.example.serenity.data.mapper.FavoriteProductEntityMapperImpl
import com.example.serenity.data.mapper.ListMapper
import com.example.serenity.data.mapper.Mapper
import com.example.serenity.data.mapper.ProductUiDataListMapperImpl
import com.example.serenity.data.mapper.ProductUiDataMapperImpl
import com.example.serenity.domain.model.ProductUiData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindProductUiDataListMapper(
        productUiDataListMapperImpl: ProductUiDataListMapperImpl
    ): ListMapper<Product, ProductUiData>

    @Binds
    @Singleton
    abstract fun bindProductUiDataMapper(
        productUiDataMapperImpl: ProductUiDataMapperImpl
    ): Mapper<Product, ProductUiData>

    @Binds
    @Singleton
    abstract fun bindFavoriteProductEntityMapper(
        favoriteProductEntityMapperImpl: FavoriteProductEntityMapperImpl
    ): Mapper<ProductUiData, FavoriteProductEntity>
}