package com.example.serenity.ui.cart

import androidx.recyclerview.widget.RecyclerView
import com.example.serenity.databinding.AdapterProductItemBinding
import com.example.serenity.domain.model.ProductUiData

class CartProductViewHolder(private val binding: AdapterProductItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: ProductUiData, onDeleteClick: (ProductUiData) -> Unit) {
        binding.productComponent.setProductData(data, isCart = true)
        binding.productComponent.onDeleteIconClickListener = {
            onDeleteClick(data)
        }
    }
}