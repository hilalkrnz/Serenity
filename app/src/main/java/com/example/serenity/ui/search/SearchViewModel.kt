package com.example.serenity.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.NetworkResponseState
import com.example.serenity.domain.model.ProductUiData
import com.example.serenity.domain.repository.SerenityRepository
import com.example.serenity.ui.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val serenityRepository: SerenityRepository
) : ViewModel() {

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> get() = _searchState

    fun searchProducts(searchProduct: String) {
        viewModelScope.launch {
            serenityRepository.searchProduct(searchProduct).collectLatest { response ->
                _searchState.value = SearchState.Loading
                _searchState.value = when (response) {
                    is NetworkResponseState.Success -> SearchState.SuccessState(response.result)
                    is NetworkResponseState.Fail -> SearchState.EmptyScreen(response.failMessage)
                    is NetworkResponseState.Error -> SearchState.ShowPopUp(response.errorMessage)
                }
            }
        }
    }

}

sealed interface SearchState {
    data object Loading : SearchState
    data class SuccessState(val products: List<ProductUiData>) : SearchState
    data class EmptyScreen(val failMessage: String) : SearchState
    data class ShowPopUp(val errorMessage: String) : SearchState
}