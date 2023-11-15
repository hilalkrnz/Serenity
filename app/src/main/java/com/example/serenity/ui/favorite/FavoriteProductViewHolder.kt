package com.example.serenity.ui.favorite

import androidx.recyclerview.widget.RecyclerView
import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.databinding.AdapterProductItemBinding
import com.example.serenity.domain.model.ProductUiData

class FavoriteProductViewHolder(private val binding: AdapterProductItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: FavoriteProductEntity, onFavoriteClick: (FavoriteProductEntity) -> Unit) {
        binding.productComponent.setFavoriteProductData(data, true)
        binding.productComponent.onFavoriteIconClickListener = {
            onFavoriteClick(data)
        }
    }
}