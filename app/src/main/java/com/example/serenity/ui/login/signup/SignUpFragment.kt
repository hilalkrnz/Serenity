package com.example.serenity.ui.login.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.serenity.R
import com.example.serenity.common.gone
import com.example.serenity.common.viewBinding
import com.example.serenity.common.visible
import com.example.serenity.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        observeData()
    }

    private fun setOnClickListener() {
        with(binding) {
            btnSignUp.setOnClickListener {
                viewModel.signUp(
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etConfirmPassword.text.toString()
                )
            }
        }
    }


    private fun observeData() = with(binding) {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SignUpState.Loading -> progressBar.visible()

                is SignUpState.GoToHome -> {
                    progressBar.gone()
                    findNavController().navigate(R.id.signUpToMainGraph)
                }

                is SignUpState.ShowPopUp -> {
                    progressBar.gone()
                    when (state.tag) {
                        "EMPTY_EMAIL" -> binding.textInputLayoutEmail.error = state.errorMessage
                        "EMPTY_PASSWORD" -> binding.textInputLayoutPassword.error = state.errorMessage
                        "EMPTY_CONFIRM_PASSWORD" -> binding.textInputLayoutConfirmPassword.error = state.errorMessage
                        "PASSWORD_LENGTH" -> binding.textInputLayoutPassword.error = state.errorMessage
                        "PASSWORD_MISMATCH" -> binding.textInputLayoutConfirmPassword.error = state.errorMessage
                    }
                }
            }
        }
    }

}