package com.example.serenity.data.mapper

import com.example.serenity.data.dto.Product
import com.example.serenity.domain.model.ProductUiData
import javax.inject.Inject

class ProductUiDataListMapperImpl @Inject constructor() : ListMapper<Product, ProductUiData> {
    override fun map(input: List<Product>?): List<ProductUiData> {
        return input?.map {
            ProductUiData(
                id = it.id,
                title = it.title.orEmpty(),
                price = it.price,
                salePrice = it.salePrice,
                description = it.description.orEmpty(),
                category = it.category.orEmpty(),
                imageOne = it.imageOne.orEmpty(),
                rate = it.rate,
                saleState = it.saleState
            )
        } ?: emptyList()
    }
}