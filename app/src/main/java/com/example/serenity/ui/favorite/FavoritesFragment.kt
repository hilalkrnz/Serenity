package com.example.serenity.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.serenity.R
import com.example.serenity.common.gone
import com.example.serenity.common.viewBinding
import com.example.serenity.common.visible
import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel by viewModels<FavoritesViewModel>()

    private val adapter = FavoriteProductsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteProducts()

        setUpRecyclerView()
        observeUiState()
        setOnClickFavoriteProductItem()
        removeFromFavorite()
    }

    private fun setUpRecyclerView() {
        binding.favoriteProductsRv.adapter = adapter
    }

    private fun observeUiState() = with(binding) {
        viewModel.getFavoriteProducts.observe(viewLifecycleOwner) { state ->
            when (state) {
                FavoriteState.Loading -> progressBar.visible()

                is FavoriteState.SuccessState -> {
                    progressBar.gone()
                    handleSuccessFavoriteState(state.products)
                }

                is FavoriteState.EmptyScreen -> {
                    progressBar.gone()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is FavoriteState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }

        }
    }

    private fun handleSuccessFavoriteState(data: List<FavoriteProductEntity>) {
        adapter.updateData(data)
    }

    private fun setOnClickFavoriteProductItem() {
        adapter.setOnItemClickListener {
            val productId = adapter.getItem(it).productId
            if (productId != null) {
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(productId)
                findNavController().navigate(action)
            }
        }
    }

    private fun removeFromFavorite() {
        adapter.setOnFavoriteIconClickListener { data ->
            val productId = data.productId
            if (productId != null) {
                viewModel.removeFromFavorite(productId)
            }
        }
    }
}