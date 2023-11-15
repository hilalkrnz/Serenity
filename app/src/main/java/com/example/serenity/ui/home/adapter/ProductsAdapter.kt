package com.example.serenity.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serenity.common.inflateAdapterItem
import com.example.serenity.databinding.AdapterProductItemBinding
import com.example.serenity.domain.model.ProductUiData

class ProductsAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private val productsList = mutableListOf<ProductUiData>()

    private var onItemClickListener: ((Int) -> Unit)? = null

    private var onFavoriteIconClickListener: ((ProductUiData) -> Unit)? = null

    private var favoritesProductMap = mutableMapOf<Int, Boolean>()

    fun setOnItemClickListener(itemClickListener: ((Int) -> Unit)?) {
        this.onItemClickListener = itemClickListener
    }

    fun setOnFavoriteIconClickListener(iconClickListener: ((ProductUiData) -> Unit)?) {
        this.onFavoriteIconClickListener = iconClickListener
    }

    fun updateData(newList: List<ProductUiData>, onlyOnSale: Boolean = false) {
        productsList.apply {
            clear()
            val filteredList = if (onlyOnSale) {
                newList.filter { it.saleState == true }
            } else {
                newList
            }
            addAll(filteredList)
            notifyDataSetChanged()
        }
    }

    fun updateFavoriteProduct(favoritesMap: Map<Int, Boolean>) {
        this.favoritesProductMap.putAll(favoritesMap)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent.inflateAdapterItem(AdapterProductItemBinding::inflate))
    }

    fun getItem(position: Int) = productsList[position]

    override fun getItemCount() = productsList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productsList[position]
        val isFavorite = favoritesProductMap[item.id] ?: false
        holder.onBind(item, isFavorite) { productUiData ->
            onFavoriteIconClickListener?.invoke(productUiData)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(productsList.indexOf(item))
        }
    }
}