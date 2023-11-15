package com.example.serenity.ui.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serenity.data.NetworkResponseState
import com.example.serenity.data.repository.AuthRepository
import com.example.serenity.ui.home.HomeState
import com.example.serenity.ui.login.signin.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> get() = _signUpState

    fun signUp(email: String, password: String, confirmPassword: String) = viewModelScope.launch {
        if (checkFields(email, password, confirmPassword)) {
            _signUpState.value = SignUpState.Loading

            _signUpState.value = when (val result = authRepository.signUp(email, password)) {
                is NetworkResponseState.Success -> SignUpState.GoToHome
                is NetworkResponseState.Fail -> SignUpState.ShowPopUp(errorMessage = result.failMessage)
                is NetworkResponseState.Error -> SignUpState.ShowPopUp(errorMessage = result.errorMessage)
            }
        }
    }

    private fun checkFields(email: String, password: String, confirmPassword: String): Boolean {
        return when {
            email.isEmpty() -> {
                _signUpState.value =
                    SignUpState.ShowPopUp("EMPTY_EMAIL", "Email field cannot be left blank")
                false
            }

            password.isEmpty() -> {
                _signUpState.value =
                    SignUpState.ShowPopUp("EMPTY_PASSWORD", "Password field cannot be left blank")
                false
            }

            password.length < 6 -> {
                _signUpState.value = SignUpState.ShowPopUp(
                    "PASSWORD_LENGTH",
                    "Password field cannot be shorter than 6 characters"
                )
                false
            }

            confirmPassword.isEmpty() -> {
                _signUpState.value =
                    SignUpState.ShowPopUp("EMPTY_CONFIRM_PASSWORD", "Confirm Password field cannot be left blank")
                false
            }

            password != confirmPassword -> {
                _signUpState.value = SignUpState.ShowPopUp("PASSWORD_MISMATCH", "Password and Confirm Password do not match")
                false
            }

            else -> true
        }
    }
}

sealed interface SignUpState {
    data object Loading : SignUpState
    data object GoToHome : SignUpState
    data class ShowPopUp(val tag: String? = null, val errorMessage: String) : SignUpState
}