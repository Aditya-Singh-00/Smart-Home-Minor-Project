package com.aditya.smarthouse.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

private const val TAG = "DeviceStatusApi"

class DeviceConfigApi(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {

    fun setDeviceConfig(id: String, name: String, vector: Int) {
        val email = firebaseAuth.currentUser?.email
        email?.let {
            val index = it.indexOf("@")
            val key = it.substring(0,index)
            val userDbRef = firebaseDatabase.getReference(key)
            userDbRef.child(id).child("name").setValue(name).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.i(TAG,"Device name updated ")
                } else {
                    Log.e(TAG, "Error occurred while updating device vector")
                }
            }
            userDbRef.child(id).child("vector").setValue(vector).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.i(TAG,"Device vector updated")
                } else {
                    Log.e(TAG, " Error occurred while updating device vector")
                }
            }
        }
    }
}
