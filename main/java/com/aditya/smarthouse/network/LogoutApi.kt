package com.aditya.smarthouse.network

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LogoutApi @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun logout() {
        firebaseAuth.signOut()
    }
}