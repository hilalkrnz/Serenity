package com.example.serenity.data.mapper

import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.domain.model.ProductUiData
import javax.inject.Inject

class FavoriteProductEntityMapperImpl @Inject constructor() :
    Mapper<ProductUiData, FavoriteProductEntity> {
    override fun map(input: ProductUiData?): FavoriteProductEntity {
        return FavoriteProductEntity(
            productId = input?.id,
            title = input?.title.orEmpty(),
            price = input?.price?.toDouble(),
            description = input?.description.orEmpty(),
            category = input?.category.orEmpty(),
            imageOne = input?.imageOne.orEmpty(),
            rate = input?.rate,
            saleState = input?.saleState
        )
    }
}