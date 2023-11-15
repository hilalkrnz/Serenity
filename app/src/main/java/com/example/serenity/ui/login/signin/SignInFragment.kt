package com.example.serenity.ui.login.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.serenity.R
import com.example.serenity.common.gone
import com.example.serenity.common.viewBinding
import com.example.serenity.common.visible
import com.example.serenity.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        observeData()
    }

    private fun setOnClickListener() {
        with(binding) {
            btnSignIn.setOnClickListener {
                viewModel.signIn(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }

            createAccount.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

    private fun observeData() = with(binding) {
        viewModel.signInState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SignInState.Loading -> progressBar.visible()

                is SignInState.GoToHome -> {
                    progressBar.gone()
                    findNavController().navigate(R.id.signInToMainGraph)
                }

                is SignInState.ShowPopUp -> {
                    progressBar.gone()
                    when (state.tag) {
                        "EMPTY_EMAIL" -> binding.textInputLayoutEmail.error = state.errorMessage
                        "EMPTY_PASSWORD" -> binding.textInputLayoutPassword.error = state.errorMessage
                        "PASSWORD_LENGTH" -> binding.textInputLayoutPassword.error = state.errorMessage
                    }
                }
            }
        }
    }

}