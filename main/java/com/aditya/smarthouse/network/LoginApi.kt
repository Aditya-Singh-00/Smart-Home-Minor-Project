package com.aditya.smarthouse.network

import android.util.Log
import com.aditya.smarthouse.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "LoginApi"

class LoginApi @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun loginUser(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val user = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
            if(user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("Something went wrong")
            }
        } catch (e: Exception) {
            Log.e(TAG,"Login Failed: ${e.localizedMessage}")
            Resource.Error("Login Failed: ${e.localizedMessage}")
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

}