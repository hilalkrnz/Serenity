package com.example.serenity.data

sealed interface NetworkResponseState<out T : Any> {
    data class Success<out T : Any>(val result: T) : NetworkResponseState<T>
    data class Error(val errorMessage: String) : NetworkResponseState<Nothing>
    data class Fail(val failMessage: String) : NetworkResponseState<Nothing>
}