package com.example.serenity.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.serenity.R
import com.example.serenity.common.gone
import com.example.serenity.common.viewBinding
import com.example.serenity.common.visible
import com.example.serenity.databinding.FragmentCartBinding
import com.example.serenity.domain.model.ProductUiData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private val adapter = CartProductListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartProducts()

        setUpRecyclerView()
        observeUiData()
        setOnClickListener()
    }

    private fun setUpRecyclerView() {
        binding.cartProductsRv.adapter = adapter
    }

    private fun observeUiData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                CartState.Loading -> progressBar.visible()

                is CartState.SuccessState -> {
                    progressBar.gone()
                    adapter.updateData(emptyList())
                    tvEmpty.visible()
                    tvEmpty.text = getString(R.string.cart_empty_message)
                }

                is CartState.ProductRemoved -> {
                    adapter.removeProduct(state.productId)
                }

                is CartState.SuccessListState -> {
                    progressBar.gone()
                    handleSuccessCartState(state.products)
                    paymentButton.isEnabled = state.products.isNotEmpty()
                }

                is CartState.EmptyScreen -> {
                    progressBar.gone()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                    paymentButton.isEnabled = false
                }

                is CartState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }

        }
    }

    private fun handleSuccessCartState(data: List<ProductUiData>) {
        adapter.updateData(data)
    }

    private fun setOnClickListener() {
        binding.deleteAll.setOnClickListener {
            viewModel.clearAllProductFromCart()
        }

        adapter.setOnDeleteIconClickListener { data ->
            data.id?.let {
                viewModel.deleteProductFromCart(it)
                println(it)
            }
        }

        adapter.setOnItemClickListener {
            val productId = adapter.getItem(it).id
            if (productId != null) {
                val action =
                    CartFragmentDirections.actionCartFragmentToDetailFragment(productId)
                findNavController().navigate(action)
            }
        }

        binding.paymentButton.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToPaymentFragment())
        }
    }
}