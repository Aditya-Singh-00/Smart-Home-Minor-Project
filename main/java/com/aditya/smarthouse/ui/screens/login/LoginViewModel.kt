package com.aditya.smarthouse.ui.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.smarthouse.repository.SmartHouseRepository
import com.aditya.smarthouse.util.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val smartHouseRepository: SmartHouseRepository
) : ViewModel() {

    private val _user: MutableState<FirebaseUser?> = mutableStateOf(null)
    val user: State<FirebaseUser?> = _user

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    private val _showProgressBar = mutableStateOf(false)
    val showProgressBar: State<Boolean> = _showProgressBar

    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _emailTextError = mutableStateOf(false)
    val emailTextError : State<Boolean> = _emailTextError

    private val _passwordTextError = mutableStateOf(false)
    val passwordTextError: State<Boolean> = _passwordTextError

    fun setEmailText(value: String) {
        _emailText.value = value
        _emailTextError.value = false
    }

    fun setPasswordText(value: String) {
        _passwordText.value = value
        _passwordTextError.value = false
    }

    fun loginUser() {
        when {
            emailText.value.isBlank() -> {
                _emailTextError.value = true
                return
            }
            passwordText.value.isBlank() -> {
                _passwordTextError.value = true
            }
        }
        _showProgressBar.value = true
        viewModelScope.launch {
            val response = smartHouseRepository.loginUser(
                email = emailText.value,
                password = passwordText.value)
            when (response) {
                is Resource.Success -> {
                    _user.value = response.data
                }
                is Resource.Error -> {
                    _errorMessage.value = response.message.toString()
                }
            }
        }.invokeOnCompletion {
            _showProgressBar.value = false
        }
    }

    fun resetErrorMessage() {
        _errorMessage.value = ""
    }
}