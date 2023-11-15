package com.example.serenity.ui.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState> get() = _signInState

    init {
        viewModelScope.launch {
            checkUser()
        }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        if (checkFields(email, password)) {
            _signInState.value = SignInState.Loading

            _signInState.value = when (val result = authRepository.signIn(email, password)) {
                is NetworkResponseState.Success -> SignInState.GoToHome
                is NetworkResponseState.Fail -> SignInState.ShowPopUp(errorMessage = result.failMessage)
                is NetworkResponseState.Error -> SignInState.ShowPopUp(errorMessage = result.errorMessage)
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                _signInState.value =
                    SignInState.ShowPopUp("EMPTY_EMAIL", "Email field cannot be left blank")
                false
            }

            password.isEmpty() -> {
                _signInState.value =
                    SignInState.ShowPopUp("EMPTY_PASSWORD", "Password field cannot be left blank")
                false
            }

            password.length < 6 -> {
                _signInState.value = SignInState.ShowPopUp(
                    "PASSWORD_LENGTH",
                    "Password field cannot be shorter than 6 characters"
                )
                false
            }

            else -> true
        }
    }

    private fun checkUser() = viewModelScope.launch {
        if (authRepository.isUserLoggedIn()) {
            _signInState.value = SignInState.GoToHome
        }
    }

}

sealed interface SignInState {
    data object Loading : SignInState
    data object GoToHome : SignInState
    data class ShowPopUp(val tag: String? = null, val errorMessage: String) : SignInState
}