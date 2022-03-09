package com.aditya.smarthouse.ui.screens.controller

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aditya.smarthouse.ui.components.AlertDialogCard
import com.aditya.smarthouse.ui.components.ControllerHeader
import com.aditya.smarthouse.ui.components.DeviceCard
import com.aditya.smarthouse.ui.components.SliderCard
import com.aditya.smarthouse.util.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun ControllerScreen(
    navController: NavController,
    controllerViewModel: ControllerViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        val devices = controllerViewModel.devices.value
        val greeting = controllerViewModel.greeting.value
        val username = controllerViewModel.username.value
        val logoutClicked = controllerViewModel.logoutIconClicked.value
        val showDeviceControl = controllerViewModel.showDeviceControl.value

        ControllerHeader(
            greeting = greeting,
            username = username,
            onLogoutIconClicked = { controllerViewModel.confirmLogout() }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (showDeviceControl != -1 && devices.isNotEmpty()) {
                SliderCard(
                    device = devices[showDeviceControl],
                    onDialogDismiss = { controllerViewModel.showControlDialog(-1) },
                    onValueChange = { deviceId, value ->
                        controllerViewModel.setDeviceStatus(deviceId, value)
                    }
                )
            }
            if (devices.isNotEmpty()) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.background(MaterialTheme.colors.surface)
                ) {
                    items(items = devices) { device ->
                        DeviceCard(
                            device = device,
                            onDeviceClick = {
                                controllerViewModel.showControlDialog(device.id)
                            },
                            onEditDeviceClick = {
                                navController.navigate(
                                    Screen.EditDeviceScreen.withArgs(device.id.toString())
                                )
                            },
                            onDeviceStatusChange = { deviceId, status ->
                                controllerViewModel.setDeviceStatus(deviceId, status)
                            }
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column {
                if (logoutClicked) {
                    AlertDialogCard(
                        title = "Logout",
                        text = "Do you want to logout?",
                        confirmButtonText = "Confirm",
                        dismissButtonText = "Dismiss",
                        onConfirmClick = {
                            controllerViewModel.logout()
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.ControllerScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    ) { controllerViewModel.dismissAlertdialog() }
                }
            }
        }
    }
}

