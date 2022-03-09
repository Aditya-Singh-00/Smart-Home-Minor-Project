package com.aditya.smarthouse.ui.screens.login

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
import com.aditya.smarthouse.R
import com.aditya.smarthouse.ui.components.AlertDialogCard
import com.aditya.smarthouse.ui.components.StandardTextField
import com.aditya.smarthouse.ui.theme.Green
import com.aditya.smarthouse.ui.theme.spaceLarge
import com.aditya.smarthouse.ui.theme.spaceMedium
import com.aditya.smarthouse.util.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    loginViewModel.user.value?.let {
        navController.popBackStack()
        navController.navigate(Screen.ControllerScreen.route)
    }

    val errorMessage = loginViewModel.errorMessage.value

    if(errorMessage.isNotEmpty()) {
        AlertDialogCard(
            title = "Error",
            text = errorMessage,
            confirmButtonText = "OK",
            onConfirmClick = { loginViewModel.resetErrorMessage() },
            onDialogDismiss = { loginViewModel.resetErrorMessage() }
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
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.body2,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(spaceMedium))
            StandardTextField(
                text = loginViewModel.emailText.value,
                onValueChange = { loginViewModel.setEmailText(it) },
                hint = stringResource(id = R.string.email),
                maxLength = 30,
                leadingIcon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email,
                isError = loginViewModel.emailTextError.value
            )
            Spacer(modifier = Modifier.height(spaceMedium))
            StandardTextField(
                text = loginViewModel.passwordText.value,
                onValueChange = { loginViewModel.setPasswordText(it) },
                hint = stringResource(id = R.string.password),
                maxLength = 30,
                leadingIcon = Icons.Filled.Lock,
                keyboardType = KeyboardType.Password,
                isError = loginViewModel.passwordTextError.value
            )
            Spacer(modifier = Modifier.height(spaceMedium))
            Button(
                onClick = {
                    loginViewModel.loginUser()
                },
                modifier = Modifier
                    .align(Alignment.End),
                enabled = !loginViewModel.showProgressBar.value
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        if (loginViewModel.showProgressBar.value) {
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
                    navController.navigate(Screen.RegisterScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                },
            text = buildAnnotatedString {
                append(stringResource(id = R.string.do_not_have_an_account))
                withStyle(style = SpanStyle(color = Green)) {
                    append(stringResource(id = R.string.sign_up))
                }
            },
            style = MaterialTheme.typography.body1
        )
    }
}
