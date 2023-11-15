package com.example.serenity.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.serenity.R
import com.example.serenity.common.gone
import com.example.serenity.common.observeTextChanges
import com.example.serenity.common.viewBinding
import com.example.serenity.common.visible
import com.example.serenity.databinding.FragmentSearchBinding
import com.example.serenity.domain.model.ProductUiData
import com.example.serenity.ui.home.HomeFragmentDirections
import com.example.serenity.ui.home.adapter.ProductsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()
    private val adapter = SearchProductListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeSearchTextChanges()
        observeUiData()
        setOnClickProductItem()
    }

    private fun setUpRecyclerView() {
        binding.searchRv.adapter = adapter
    }

    private fun observeUiData() = with(binding) {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchState.Loading -> progressBar.visible()

                is SearchState.SuccessState -> {
                    progressBar.gone()
                    handleSuccessSearchState(state.products)
                    tvEmpty.gone()
                }

                is SearchState.EmptyScreen -> {
                    progressBar.gone()
                    handleSuccessSearchState(emptyList())
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is SearchState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }

        }
    }

    private fun handleSuccessSearchState(data: List<ProductUiData>) {
        adapter.updateData(data)
    }


    private fun observeSearchTextChanges() {
        binding.searchEditText.observeTextChanges()
            .debounce(SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS)
            .onEach { searchText ->
                if (searchText.length >= MINIMUM_SEARCH_LENGTH) {
                    viewModel.searchProducts(searchText)
                } else {
                    handleSuccessSearchState(emptyList())
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setOnClickProductItem() {
        adapter.setOnItemClickListener {position ->
            val productId = adapter.getItem(position).id
            if (productId != null) {
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(productId)
                findNavController().navigate(action)
            }
        }

    }

    companion object {
        private const val MINIMUM_SEARCH_LENGTH = 3
        private const val SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS = 200L
    }

}