package com.aditya.smarthouse.ui.screens.controller

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.smarthouse.domain.models.Device
import com.aditya.smarthouse.repository.SmartHouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val TAG = "ControllerViewModel"

@ExperimentalCoroutinesApi
@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val smartHouseRepository: SmartHouseRepository
) : ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _greeting = mutableStateOf("")
    val greeting: State<String> = _greeting

    private val _devices: MutableState<List<Device>> = mutableStateOf(listOf())
    val devices: State<List<Device>> = _devices

    private val _logoutIconClicked = mutableStateOf(false)
    val logoutIconClicked: State<Boolean> = _logoutIconClicked

    private val _showDeviceControl = mutableStateOf(-1)
    val showDeviceControl: State<Int> = _showDeviceControl

    init {
        getGreetingMessage()
        getCurrentUser()
        getDeviceStatus()
    }

    fun setDeviceStatus(deviceId: Int, status: Int) {
        viewModelScope.launch {
            delay(300)
            smartHouseRepository.setDeviceStatus(deviceId.toString(), status)
        }
    }

    fun confirmLogout() {
        _logoutIconClicked.value = true
    }

    fun dismissAlertdialog() {
        _logoutIconClicked.value = false
    }

    fun logout() {
        smartHouseRepository.logout()
    }

    fun showControlDialog(id: Int) {
        _showDeviceControl.value = id
    }

    private fun getGreetingMessage() {
        val c = Calendar.getInstance()
        _greeting.value = when (c.get(Calendar.HOUR_OF_DAY)) {
            in 4..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            in 21..23 -> "Good Night"
            else -> "Hello"
        }
    }

    private fun getCurrentUser() {
        _username.value = smartHouseRepository.getCurrentUser()?.displayName ?: ""
    }

    private fun getDeviceStatus() {
        viewModelScope.launch {
            smartHouseRepository.getDeviceStatus().collect {
                Log.i(TAG, "Data updated in ViewModel")
                Log.i(TAG, "${devices.value.size}")
                _devices.value = it
            }
        }
    }
}

