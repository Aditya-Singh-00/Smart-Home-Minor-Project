package com.aditya.smarthouse.ui.screens.register

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
class RegisterViewModel @Inject constructor(
    private val smartHouseRepository: SmartHouseRepository
) : ViewModel() {

    private val _user: MutableState<FirebaseUser?> = mutableStateOf(null)
    val user: State<FirebaseUser?> = _user

    private val _showProgressBar = mutableStateOf(false)
    val showProgressBar: State<Boolean> = _showProgressBar

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _usernameTextError = mutableStateOf(false)
    val usernameTextError: State<Boolean> = _usernameTextError

    private val _emailTextError = mutableStateOf(false)
    val emailTextError: State<Boolean> = _emailTextError

    private val _passwordTextError = mutableStateOf(false)
    val passwordTextError: State<Boolean> = _passwordTextError

    fun setUsernameText(value: String) {
        _usernameText.value = value
        _usernameTextError.value = false
    }

    fun setEmailText(value: String) {
        _emailText.value = value
        _emailTextError.value = false
    }

    fun setPasswordText(value: String) {
        _passwordText.value = value
        _passwordTextError.value = false
    }

    fun registerUser() {
        when {
            emailText.value.isBlank() -> {
                _emailTextError.value = true
                return
            }
            usernameText.value.isBlank() -> {
                _usernameTextError.value = true
                return
            }
            passwordText.value.isBlank() -> {
                _passwordTextError.value = true
                return
            }
        }
        _showProgressBar.value = true
        viewModelScope.launch {
            val response = smartHouseRepository.registerUser(
                    email = emailText.value,
                    password = passwordText.value,
                    username = usernameText.value
                )
             when (response){
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