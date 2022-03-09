package com.aditya.smarthouse.repository

import com.aditya.smarthouse.domain.models.Device
import com.aditya.smarthouse.network.*
import com.aditya.smarthouse.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmartHouseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {

    fun logout() {
        val loginApi = LogoutApi(firebaseAuth)
        loginApi.logout()
    }

    fun getCurrentUser(): FirebaseUser? {
        val loginApi = LoginApi(firebaseAuth)
        return loginApi.getCurrentUser()
    }

    suspend fun loginUser(email: String, password: String): Resource<FirebaseUser> {
        val loginApi = LoginApi(firebaseAuth)
        return loginApi.loginUser(email,password)
    }

    suspend fun registerUser(
        email: String,
        password: String,
        username: String
    ): Resource<FirebaseUser> {
        val registerApi = RegisterApi(firebaseAuth, firebaseDatabase)
        return registerApi.registerUser(email,password,username)
    }

    fun setDeviceConfig(id: String, name: String, vector: Int) {
        val deviceConfigApi = DeviceConfigApi(firebaseAuth, firebaseDatabase)
        deviceConfigApi.setDeviceConfig(id,name,vector)
    }

    fun setDeviceStatus(id: String, value: Int) {
        val deviceStatusApi = DeviceStatusApi(firebaseAuth, firebaseDatabase)
        deviceStatusApi.setDeviceStatus(id,value)
    }

    @ExperimentalCoroutinesApi
    fun getDeviceStatus(): Flow<MutableList<Device>> {
        val deviceStatusApi = DeviceStatusApi(firebaseAuth, firebaseDatabase)
        return deviceStatusApi.getDeviceStatus()
    }

}
