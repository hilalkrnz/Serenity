package com.example.serenity.data.repository

import com.example.serenity.data.NetworkResponseState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    fun isUserLoggedIn() = firebaseAuth.currentUser != null

    fun getUserId() = firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun signIn(email: String, password: String): NetworkResponseState<Unit> {

        return try {

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            if (result.user != null) {
                NetworkResponseState.Success(Unit)
            } else {
                NetworkResponseState.Error("An error occurred")
            }
        } catch (e: FirebaseAuthException) {
            NetworkResponseState.Error(e.message.orEmpty())
        }
    }

    suspend fun signUp(email: String, password: String): NetworkResponseState<Unit> {

        return try {

            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            if (result.user != null) {
                NetworkResponseState.Success(Unit)
            } else {
                NetworkResponseState.Error("Bir hata oluştu")
            }
        } catch (e: FirebaseAuthException) {
            NetworkResponseState.Error(e.message.orEmpty())
        }
    }

    fun logOut() = firebaseAuth.signOut()
}