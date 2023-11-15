package com.example.serenity.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.serenity.databinding.AdapterProductItemBinding
import com.example.serenity.domain.model.ProductUiData

class ProductViewHolder(private val binding: AdapterProductItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: ProductUiData, isStarredListener: Boolean,  onFavoriteClick: (ProductUiData) -> Unit) {
        binding.productComponent.setProductData(data, isStarredListener)
        binding.productComponent.onFavoriteIconClickListener = {
            onFavoriteClick(data)
        }
    }
}