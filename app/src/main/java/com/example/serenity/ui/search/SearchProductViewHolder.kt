package com.example.serenity.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.example.serenity.databinding.AdapterProductItemBinding
import com.example.serenity.domain.model.ProductUiData

class SearchProductViewHolder(
    private val binding: AdapterProductItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: ProductUiData) {
        binding.productComponent.setProductData(data, isSearch = true)
    }
}