package com.example.serenity.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.serenity.R
import com.example.serenity.common.gone
import com.example.serenity.common.viewBinding
import com.example.serenity.common.visible
import com.example.serenity.databinding.FragmentHomeBinding
import com.example.serenity.domain.model.ProductUiData
import com.example.serenity.ui.home.adapter.ProductsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val productListAdapter = ProductsAdapter()
    private val saleProductListAdapter = ProductsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllProducts()

        setUpRecyclerView()
        observeUiData()
        setOnClickProductItem()
        changeProductFavoriteStatus()
        setOnClickLogOut()
    }

    private fun setUpRecyclerView() {
        binding.productsRv.adapter = productListAdapter
        binding.saleProductsRv.adapter = saleProductListAdapter

    }

    private fun observeUiData() = with(binding) {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> {
                    progressBar.visible()
                    discountTv.gone()
                    allProductsTv.gone()
                }

                is HomeState.SuccessState -> {
                    progressBar.gone()
                    discountTv.visible()
                    allProductsTv.visible()
                    handleSuccessHomeState(state.products)
                }

                is HomeState.EmptyScreen -> {
                    progressBar.gone()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is HomeState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                HomeState.GoToSignIn -> {
                    findNavController().navigate(R.id.homeToMain)
                }
            }

        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { favoritesMap ->
            productListAdapter.updateFavoriteProduct(favoritesMap)
            saleProductListAdapter.updateFavoriteProduct(favoritesMap)
        }
    }

    private fun handleSuccessHomeState(data: List<ProductUiData>) {
        productListAdapter.updateData(data)
        saleProductListAdapter.updateData(data, true)
        val productIds = data.mapNotNull { it.id }
        checkFavoriteProductState(productIds)
    }

    private fun checkFavoriteProductState(productId: List<Int>) {
        viewModel.checkFavoritedMessages(productId)
    }

    private fun setOnClickProductItem() {
        val itemClickListener: (Int, ProductsAdapter) -> Unit = { position, adapter ->
            val productId = adapter.getItem(position).id
            if (productId != null) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(productId)
                findNavController().navigate(action)
            }
        }

        productListAdapter.setOnItemClickListener { position ->
            itemClickListener(position, productListAdapter)
        }

        saleProductListAdapter.setOnItemClickListener { position ->
            itemClickListener(position, saleProductListAdapter)
        }
    }

    private fun setOnClickLogOut() {
        binding.ivLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.logOut()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun changeProductFavoriteStatus() {
        setFavoriteClickListener(productListAdapter)
        setFavoriteClickListener(saleProductListAdapter)
    }

    private fun setFavoriteClickListener(adapter: ProductsAdapter) {
        adapter.setOnFavoriteIconClickListener { productUiData ->
            val productId = productUiData.id ?: return@setOnFavoriteIconClickListener

            viewModel.isFavorite.value?.get(productId)?.let { isFavorite ->
                if (isFavorite) {
                    viewModel.removeFromFavorite(productId)
                } else {
                    viewModel.addToFavorite(productUiData)
                }
            }
        }
    }

}