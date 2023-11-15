package com.example.serenity.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.repository.AuthRepository
import com.example.serenity.domain.model.ProductUiData
import com.example.serenity.domain.repository.SerenityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val serenityRepository: SerenityRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState


    fun getCartProducts() = viewModelScope.launch {
        serenityRepository.getProductsFromCart(authRepository.getUserId())
            .collectLatest { response ->
                _cartState.value = CartState.Loading
                _cartState.value = when (response) {
                    is NetworkResponseState.Success -> CartState.SuccessListState(response.result)
                    is NetworkResponseState.Fail -> CartState.EmptyScreen(response.failMessage)
                    is NetworkResponseState.Error -> CartState.ShowPopUp(response.errorMessage)
                }
            }
    }

    fun deleteProductFromCart(productId: Int) {
        viewModelScope.launch {
            _cartState.value =
                when (val result = serenityRepository.deleteProductFromCart(
                    authRepository.getUserId(),
                    productId
                )) {
                    is NetworkResponseState.Success -> CartState.ProductRemoved(productId)
                    is NetworkResponseState.Fail -> CartState.EmptyScreen(result.failMessage)
                    is NetworkResponseState.Error -> CartState.ShowPopUp(result.errorMessage)
                }
        }
    }

    fun clearAllProductFromCart() {
        viewModelScope.launch {
            _cartState.value =
                when (val result =
                    serenityRepository.clearAllProductFromCart(authRepository.getUserId())) {
                    is NetworkResponseState.Success -> CartState.SuccessState
                    is NetworkResponseState.Fail -> CartState.EmptyScreen(result.failMessage)
                    is NetworkResponseState.Error -> CartState.ShowPopUp(result.errorMessage)
                }
        }
        println(authRepository.getUserId())
    }

}

sealed interface CartState {
    data object Loading : CartState
    data object SuccessState : CartState
    data class ProductRemoved(val productId: Int) : CartState
    data class SuccessListState(val products: List<ProductUiData>) : CartState
    data class EmptyScreen(val failMessage: String) : CartState
    data class ShowPopUp(val errorMessage: String) : CartState
}