package com.aditya.smarthouse

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.aditya.smarthouse.repository.SmartHouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val smartHouseRepository: SmartHouseRepository
): ViewModel() {

    private val _userLoggedIn: MutableState<Boolean?> = mutableStateOf(null)
    val userLoggedIn: State<Boolean?> = _userLoggedIn

    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        _userLoggedIn.value = smartHouseRepository.getCurrentUser() != null
    }

}