package com.example.serenity.ui.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serenity.common.inflateAdapterItem
import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.databinding.AdapterProductItemBinding
import com.example.serenity.domain.model.ProductUiData

class FavoriteProductsAdapter : RecyclerView.Adapter<FavoriteProductViewHolder>() {

    private val favoriteProductsList = mutableListOf<FavoriteProductEntity>()

    private var onItemClickListener: ((Int) -> Unit)? = null

    private var onFavoriteIconClickListener: ((FavoriteProductEntity) -> Unit)? = null

    fun setOnItemClickListener(itemClickListener: ((Int) -> Unit)?) {
        this.onItemClickListener = itemClickListener
    }

    fun setOnFavoriteIconClickListener(iconClickListener: ((FavoriteProductEntity) -> Unit)?) {
        this.onFavoriteIconClickListener = iconClickListener
    }



    fun updateData(newList: List<FavoriteProductEntity>) {
        favoriteProductsList.apply {
            clear()
            addAll(newList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        return FavoriteProductViewHolder(parent.inflateAdapterItem(AdapterProductItemBinding::inflate))
    }

    fun getItem(position: Int) = favoriteProductsList[position]

    override fun getItemCount() = favoriteProductsList.size

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        val item = favoriteProductsList[position]
        holder.onBind(item) { favoriteProduct ->
            onFavoriteIconClickListener?.invoke(favoriteProduct)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(favoriteProductsList.indexOf(item))
        }
    }
}