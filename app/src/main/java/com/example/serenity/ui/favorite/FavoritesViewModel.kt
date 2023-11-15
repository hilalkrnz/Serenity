package com.example.serenity.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.domain.model.ProductUiData
import com.example.serenity.domain.repository.FavoriteProductRepository
import com.example.serenity.ui.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteProductRepository: FavoriteProductRepository
): ViewModel() {

    private val _getFavoriteProducts = MutableLiveData<FavoriteState>()
    val getFavoriteProducts: LiveData<FavoriteState> get() = _getFavoriteProducts

    fun getFavoriteProducts() {
        viewModelScope.launch {
            favoriteProductRepository.getFavoriteProducts().collectLatest { response ->
                _getFavoriteProducts.value = FavoriteState.Loading
                _getFavoriteProducts.value = when (response) {
                    is NetworkResponseState.Success -> FavoriteState.SuccessState(response.result)
                    is NetworkResponseState.Fail -> FavoriteState.EmptyScreen(response.failMessage)
                    is NetworkResponseState.Error -> FavoriteState.ShowPopUp(response.errorMessage)
                }
            }
        }
    }

    fun removeFromFavorite(productId: Int) {
        viewModelScope.launch {
            favoriteProductRepository.removeFromFavorite(productId)
            val updatedFavorites = favoriteProductRepository.getFavoriteProducts().firstOrNull()
            _getFavoriteProducts.value = when (updatedFavorites) {
                is NetworkResponseState.Success -> FavoriteState.SuccessState(updatedFavorites.result)
                is NetworkResponseState.Fail -> FavoriteState.EmptyScreen(updatedFavorites.failMessage)
                is NetworkResponseState.Error -> FavoriteState.ShowPopUp(updatedFavorites.errorMessage)
                null -> FavoriteState.EmptyScreen("No data available")
            }
        }
    }
}

sealed interface FavoriteState {
    data object Loading : FavoriteState
    data class SuccessState(val products: List<FavoriteProductEntity>) : FavoriteState
    data class EmptyScreen(val failMessage: String) : FavoriteState
    data class ShowPopUp(val errorMessage: String) : FavoriteState
}