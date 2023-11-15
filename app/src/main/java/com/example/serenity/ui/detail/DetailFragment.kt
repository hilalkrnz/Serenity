package com.example.serenity.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.serenity.R
import com.example.serenity.common.gone
import com.example.serenity.common.viewBinding
import com.example.serenity.common.visible
import com.example.serenity.databinding.FragmentDetailBinding
import com.example.serenity.domain.model.ProductUiData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductById(args.productId)
        observeUiState()
        setOnClickListener()
    }

    private fun observeUiState() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailState.Loading -> progressBar.visible()

                is DetailState.Success -> {
                    progressBar.gone()
                }

                is DetailState.SuccessState -> {
                    progressBar.gone()
                    handleSuccessDetailState(state.product)
                }

                is DetailState.EmptyScreen -> {
                    progressBar.gone()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is DetailState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }

        }
    }

    private fun handleSuccessDetailState(data: ProductUiData) = with(binding) {
        Glide.with(productImage).load(data.imageOne).into(productImage)
        productTitle.text = data.title
        productPrice.text = data.price.toString()
        productDescription.text = data.description
        if (data.saleState == true) {
            productSalePrice.apply {
                text = data.salePrice.toString()
                visibility = View.VISIBLE
            }

        } else {
            productSalePrice.visibility = View.GONE
        }
    }

    private fun setOnClickListener() {
        binding.addToCart.setOnClickListener {
            viewModel.addProductToCart(args.productId)
            Toast.makeText(requireContext(), "Added to cart!", Toast.LENGTH_SHORT).show()
        }
    }

}