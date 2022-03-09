package com.aditya.smarthouse.network

import android.util.Log
import com.aditya.smarthouse.domain.models.Device
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

private const val TAG = "DeviceStatusApi"

class DeviceStatusApi(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {
    fun setDeviceStatus(id: String, value: Int) {
        val email = firebaseAuth.currentUser?.email
        email?.let {
            val index = it.indexOf("@")
            val key = it.substring(startIndex = 0,endIndex = index)
            val userDbRef = firebaseDatabase.getReference(key)
            userDbRef.child(id).child("status").setValue(value).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.i(TAG,"$id : $value")
                } else {
                    Log.e(TAG,"Error occurred")
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun getDeviceStatus() = callbackFlow {
        val user = firebaseAuth.currentUser
        user?.let {
            it.email?.let { email ->
                val index = email.indexOf("@")
                val key = email.substring(0,index)
                firebaseDatabase.getReference(key).addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val devices = mutableListOf<Device>()
                        for(deviceSnapshot in snapshot.children) {
                            deviceSnapshot.getValue(Device::class.java)?.let { device ->
                                devices.add(device)
                            }
                        }
                        Log.i(TAG,"Data updated")
                        this@callbackFlow.trySend(devices).isSuccess
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e(TAG,"Error : ${error.message}")
                    }
                })
                awaitClose()
            }
        }
    }
}