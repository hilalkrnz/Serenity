package com.example.serenity.common.watcher

import android.text.Editable
import android.text.TextWatcher

class ExpiryDateWatcher : TextWatcher {
    private var isEditing = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (isEditing) return
        isEditing = true

        val userInput = s.toString().replace(Regex("\\D"), "")

        if (userInput.length <= 4) {
            var formatted = userInput
            if (userInput.length > 2) {
                formatted = userInput.substring(0, 2) + "/" + userInput.substring(2)
            }
            if (s.toString() != formatted) {
                s.replace(0, s.length, formatted)
            }
        } else {
            s.delete(4, s.length)
        }

        isEditing = false
    }
}
