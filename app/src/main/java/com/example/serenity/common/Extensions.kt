package com.example.serenity.common

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun AppCompatEditText.observeTextChanges(): Flow<String> {
    return callbackFlow {
        val textWatcher = object : AbstractTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                trySend(s.toString())
            }
        }
        addTextChangedListener(textWatcher)

        awaitClose {
            removeTextChangedListener(textWatcher)
        }
    }.onStart {
        text?.let {
            emit(it.toString())
        }
    }
}

infix fun String.okWith(bound: Int) = length > bound

fun String?.extractPrice(): Double {
    return this?.replace("₺", "")?.trim()?.toDoubleOrNull() ?: 0.0
}