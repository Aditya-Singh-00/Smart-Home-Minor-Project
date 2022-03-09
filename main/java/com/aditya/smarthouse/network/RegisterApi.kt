package com.aditya.smarthouse.network

import android.util.Log
import com.aditya.smarthouse.data.devices
import com.aditya.smarthouse.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

private const val TAG = "RegisterApi"

class RegisterApi(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {

    suspend fun registerUser(email: String, password: String ,username: String): Resource<FirebaseUser> {
        return try {
            val user = firebaseAuth.createUserWithEmailAndPassword(email,password).await().user
            if(user != null) {
                val profile = UserProfileChangeRequest.Builder().setDisplayName(username).build()
                user.updateProfile(profile).await()
                user.sendEmailVerification()
                createUserDevicesInDatabase(email,firebaseDatabase)
                Resource.Success(user)
            } else { Resource.Error("Something went wrong") }
        } catch (e: Exception) {
            Log.e(TAG,"Registration Failed: ${e.localizedMessage}")
            Resource.Error("Registration Failed: ${e.localizedMessage}")
        }
    }
}

private suspend fun createUserDevicesInDatabase(
    email: String,
    firebaseDatabase: FirebaseDatabase
) {
    val index = email.indexOf("@")
    val key = email.substring(startIndex = 0,endIndex = index)
    val userDbRef = firebaseDatabase.getReference(key)
    devices.forEach { device ->
        userDbRef.child(device.id.toString()).setValue(device).await()
    }
}