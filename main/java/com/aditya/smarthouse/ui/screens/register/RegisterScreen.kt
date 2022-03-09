package com.aditya.smarthouse.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aditya.smarthouse.ui.components.StandardTextField
import com.aditya.smarthouse.ui.theme.Green
import com.aditya.smarthouse.ui.theme.spaceLarge
import com.aditya.smarthouse.ui.theme.spaceMedium
import com.aditya.smarthouse.util.Screen
import com.aditya.smarthouse.R
import com.aditya.smarthouse.ui.components.AlertDialogCard

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {

    registerViewModel.user.value?.let {
        navController.navigate(Screen.ControllerScreen.route)
    }

    val errorMessage = registerViewModel.errorMessage.value

    if(errorMessage.isNotEmpty()) {
        AlertDialogCard(
            title = "Error",
            text = errorMessage,
            confirmButtonText = "OK",
            onConfirmClick = { registerViewModel.resetErrorMessage() },
            onDialogDismiss = { registerViewModel.resetErrorMessage() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(spaceLarge)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.body2,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(spaceMedium))
            StandardTextField(
                text = registerViewModel.emailText.value,
                onValueChange = { registerViewModel.setEmailText(it) },
                keyboardType = KeyboardType.Email,
                maxLength = 30,
                leadingIcon = Icons.Filled.Email,
                hint = stringResource(id = R.string.email),
                isError = registerViewModel.emailTextError.value
            )
            Spacer(modifier = Modifier.height(spaceMedium))
            StandardTextField(
                text = registerViewModel.usernameText.value,
                onValueChange = { registerViewModel.setUsernameText(it) },
                keyboardType = KeyboardType.Text,
                maxLength = 30,
                leadingIcon = Icons.Filled.Person,
                hint = stringResource(id = R.string.username),
                isError = registerViewModel.usernameTextError.value
            )
            Spacer(modifier = Modifier.height(spaceMedium))
            StandardTextField(
                text = registerViewModel.passwordText.value,
                onValueChange = { registerViewModel.setPasswordText(it) },
                keyboardType = KeyboardType.Password,
                maxLength = 30,
                leadingIcon = Icons.Filled.Lock,
                hint = stringResource(id = R.string.password),
                isError = registerViewModel.passwordTextError.value
            )
            Spacer(modifier = Modifier.height(spaceMedium))
            Button(
                onClick = {
                    registerViewModel.registerUser()
                },
                modifier = Modifier
                    .align(Alignment.End),
                enabled = !registerViewModel.showProgressBar.value
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        if(registerViewModel.showProgressBar.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(spaceLarge)
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.RegisterScreen.route) {
                            inclusive = true
                        }
                    }
                },
            text = buildAnnotatedString {
                append(stringResource(id = R.string.already_have_an_account))
                withStyle(style = SpanStyle(color = Green)) {
                    append(stringResource(id = R.string.sign_in))
                }
            },
            style = MaterialTheme.typography.body1
        )
    }
}
