package com.example.serenity.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.repository.AuthRepository
import com.example.serenity.domain.model.ProductUiData
import com.example.serenity.domain.repository.SerenityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val serenityRepository: SerenityRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _detailUiState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailUiState

    fun getProductById(productId: Int) {
        viewModelScope.launch {
            _detailUiState.value =
                when (val result = serenityRepository.getProductById(productId)) {
                    is NetworkResponseState.Success -> DetailState.SuccessState(result.result)
                    is NetworkResponseState.Fail -> DetailState.EmptyScreen(result.failMessage)
                    is NetworkResponseState.Error -> DetailState.ShowPopUp(result.errorMessage)
                }
        }
    }

    fun addProductToCart(productId: Int) {
        viewModelScope.launch {
            _detailUiState.value =
                when (val result =
                    serenityRepository.addProductToCart(authRepository.getUserId(), productId)) {
                    is NetworkResponseState.Success -> DetailState.Success
                    is NetworkResponseState.Fail -> DetailState.EmptyScreen(result.failMessage)
                    is NetworkResponseState.Error -> DetailState.ShowPopUp(result.errorMessage)
                }
        }
    }
}

sealed interface DetailState {
    data object Loading : DetailState
    data object Success : DetailState
    data class SuccessState(val product: ProductUiData) : DetailState
    data class EmptyScreen(val failMessage: String) : DetailState
    data class ShowPopUp(val errorMessage: String) : DetailState
}