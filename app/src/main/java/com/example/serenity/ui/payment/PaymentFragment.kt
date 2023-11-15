package com.example.serenity.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.serenity.R
import com.example.serenity.common.viewBinding
import com.example.serenity.common.watcher.FourDigitCardFormatWatcher
import com.example.serenity.databinding.FragmentPaymentBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCreditCardFormat()
        setUpButtonClickListener()
    }

    private fun setUpCreditCardFormat() {
        binding.cardNumber.editText?.addTextChangedListener(FourDigitCardFormatWatcher())
    }

    private fun setUpButtonClickListener() {
        binding.completeButton.setOnClickListener {
            if (areFieldsValid()) {
                findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToSuccessFragment())
            }
        }

    }

    private fun areFieldsValid(): Boolean {
        val isCardNumberValid = validateField(binding.cardNumber, "Please enter a card number")
        val isNameOnCardValid = validateField(binding.nameOnCard, "Please enter the name on card")
        val isExpiryMonthDateValid =
            validateField(binding.expiryMonthDate, "Please enter the expiry date")
        val isExpiryYearDateValid =
            validateField(binding.expiryYearDate, "Please enter the expiry date")
        val isCvvValid = validateField(binding.cvv, "Please enter the cvv")
        val isAdressValid = validateField(binding.adress, "Please enter the adress")


        return isCardNumberValid && isNameOnCardValid && isExpiryMonthDateValid && isExpiryYearDateValid && isCvvValid && isAdressValid
    }


    private fun validateField(layout: TextInputLayout, errorMessage: String): Boolean {
        return if (layout.editText?.text.isNullOrEmpty()) {
            layout.error = errorMessage
            false
        } else {
            layout.error = null
            true
        }
    }

}