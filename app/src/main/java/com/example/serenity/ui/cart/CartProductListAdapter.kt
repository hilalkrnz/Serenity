package com.example.serenity.ui.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serenity.common.inflateAdapterItem
import com.example.serenity.databinding.AdapterProductItemBinding
import com.example.serenity.domain.model.ProductUiData

class CartProductListAdapter : RecyclerView.Adapter<CartProductViewHolder>() {

    private val cartList = mutableListOf<ProductUiData>()
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var onDeleteIconClickListener: ((ProductUiData) -> Unit)? = null

    fun setOnItemClickListener(itemClickListener: ((Int) -> Unit)?) {
        this.onItemClickListener = itemClickListener
    }

    fun setOnDeleteIconClickListener(iconClickListener: ((ProductUiData) -> Unit)?) {
        this.onDeleteIconClickListener = iconClickListener
    }

    fun updateData(newList: List<ProductUiData>) {
        cartList.apply {
            clear()
            addAll(newList)
            notifyDataSetChanged()
        }
    }

    fun removeProduct(productId: Int) {
        val index = cartList.indexOfFirst { it.id == productId }
        if (index != -1) {
            cartList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder(parent.inflateAdapterItem(AdapterProductItemBinding::inflate))
    }

    fun getItem(position: Int) = cartList[position]

    override fun getItemCount() = cartList.size

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val item = cartList[position]
        holder.onBind(item) { productUiData ->
            onDeleteIconClickListener?.invoke(productUiData)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(cartList.indexOf(item))
        }
    }
}