package com.example.serenity.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serenity.common.inflateAdapterItem
import com.example.serenity.databinding.AdapterProductItemBinding
import com.example.serenity.domain.model.ProductUiData

class SearchProductListAdapter : RecyclerView.Adapter<SearchProductViewHolder>() {

    private val searchProductsList = mutableListOf<ProductUiData>()

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(itemClickListener: ((Int) -> Unit)?) {
        this.onItemClickListener = itemClickListener
    }

    fun updateData(newList: List<ProductUiData>) {
        searchProductsList.apply {
            clear()
            addAll(newList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        return SearchProductViewHolder(parent.inflateAdapterItem(AdapterProductItemBinding::inflate))
    }

    fun getItem(position: Int) = searchProductsList[position]

    override fun getItemCount() = searchProductsList.size

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        val item = searchProductsList[position]
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(searchProductsList.indexOf(item))
        }
    }
}