package com.example.serenity.data.mapper

import com.example.serenity.data.dto.Product
import com.example.serenity.domain.model.ProductUiData
import javax.inject.Inject

class ProductUiDataMapperImpl @Inject constructor() : Mapper<Product, ProductUiData> {
    override fun map(input: Product?): ProductUiData {
        return ProductUiData(
            id = input?.id,
            title = input?.title.orEmpty(),
            price = input?.price,
            salePrice = input?.salePrice,
            description = input?.description.orEmpty(),
            category = input?.category.orEmpty(),
            imageOne = input?.imageOne.orEmpty(),
            rate = input?.rate,
            saleState = input?.saleState
        )
    }
}