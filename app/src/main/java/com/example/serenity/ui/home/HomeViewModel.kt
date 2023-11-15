package com.example.serenity.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.database.FavoriteProductEntity
import com.example.serenity.data.mapper.FavoriteProductEntityMapperImpl
import com.example.serenity.data.repository.AuthRepository
import com.example.serenity.domain.model.ProductUiData
import com.example.serenity.domain.repository.FavoriteProductRepository
import com.example.serenity.domain.repository.SerenityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serenityRepository: SerenityRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val authRepository: AuthRepository,
    private val favoriteProductMapper: FavoriteProductEntityMapperImpl,
) : ViewModel() {

    private val _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState

    private val _isFavorite = MutableLiveData<Map<Int, Boolean>>(mapOf())
    val isFavorite: LiveData<Map<Int, Boolean>> get() = _isFavorite

    fun getAllProducts() {
        viewModelScope.launch {
            serenityRepository.getAllProduct().collectLatest { response ->
                _homeState.value = HomeState.Loading
                _homeState.value = when (response) {
                    is NetworkResponseState.Success -> HomeState.SuccessState(response.result)
                    is NetworkResponseState.Fail -> HomeState.EmptyScreen(response.failMessage)
                    is NetworkResponseState.Error -> HomeState.ShowPopUp(response.errorMessage)
                }
            }
        }
    }

    fun addToFavorite(product: ProductUiData) {
        viewModelScope.launch {
            favoriteProductRepository.addToFavorite(mapFavoriteProduct(product))
            product.id?.let { updateFavoriteStatus(it, true) }
        }
    }


    fun checkFavoritedMessages(productIds: List<Int>) {
        viewModelScope.launch {
            val currentMap = _isFavorite.value?.toMutableMap() ?: mutableMapOf()
            for (productId in productIds) {
                val count = favoriteProductRepository.checkFavoriteProduct(productId)
                val isFavorite = count > 0
                currentMap[productId] = isFavorite
            }
            _isFavorite.postValue(currentMap)
        }
    }

    fun removeFromFavorite(productId: Int) {
        viewModelScope.launch {
            favoriteProductRepository.removeFromFavorite(productId)
            updateFavoriteStatus(productId, isFavorite = false)
        }
    }

    private fun updateFavoriteStatus(productId: Int, isFavorite: Boolean) {
        val currentMap = _isFavorite.value?.toMutableMap() ?: mutableMapOf()
        currentMap[productId] = isFavorite
        _isFavorite.postValue(currentMap)
    }

    private fun mapFavoriteProduct(mapFavoriteProduct: ProductUiData?): FavoriteProductEntity {
        return favoriteProductMapper.map(mapFavoriteProduct)
    }

    fun logOut() {
        authRepository.logOut()
        _homeState.value = HomeState.GoToSignIn
    }
}

sealed interface HomeState {
    data object Loading : HomeState
    data object GoToSignIn : HomeState
    data class SuccessState(val products: List<ProductUiData>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowPopUp(val errorMessage: String) : HomeState
}