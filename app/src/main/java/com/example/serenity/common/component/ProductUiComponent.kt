package com.example.serenity.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.serenity.R
import com.example.serenity.common.visible
import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.databinding.LayoutProductBinding
import com.example.serenity.domain.model.ProductUiData

class ProductUiComponent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAtrr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAtrr) {
    private val binding = LayoutProductBinding.inflate(LayoutInflater.from(context), this, false)

    var onFavoriteIconClickListener: (() -> Unit)? = null
    var onDeleteIconClickListener: (() -> Unit)? = null

    init {
        addView(binding.root)
        binding.favoriteIcon.setOnClickListener {
            onFavoriteIconClickListener?.invoke()
        }
        binding.deleteIcon.setOnClickListener {
            onDeleteIconClickListener?.invoke()
        }
    }

    fun setProductData(
        productUiData: ProductUiData,
        isFavorited: Boolean = false,
        isSearch: Boolean = false,
        isCart: Boolean = false
    ) {
        binding.apply {
            Glide.with(productImage).load(productUiData.imageOne).into(productImage)
            productTitle.text = productUiData.title
            productPrice.text = productUiData.price.toString()
            favoriteIcon.visibility = if (isSearch || isCart) View.GONE else View.VISIBLE
            deleteIcon.visibility = if (isCart) View.VISIBLE else View.GONE
            favoriteIcon.isChecked = isFavorited
            if (productUiData.saleState == true) {
                productSalePrice.apply {
                    text = productUiData.salePrice.toString()
                    visibility = View.VISIBLE
                }
                view.visible()
                productPrice.setTextColor(ContextCompat.getColor(context, R.color.red))
            } else {
                productSalePrice.visibility = View.GONE
            }
        }
    }

    fun setFavoriteProductData(productEntity: FavoriteProductEntity, isFavorited: Boolean) {
        binding.apply {
            Glide.with(productImage).load(productEntity.imageOne).into(productImage)
            productTitle.text = productEntity.title
            productPrice.text = productEntity.price.toString()
            favoriteIcon.isChecked = isFavorited
        }
    }
}