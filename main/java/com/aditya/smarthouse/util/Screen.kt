package com.aditya.smarthouse.util

sealed class Screen(val route: String) {
    object ControllerScreen: Screen("controller_screen")
    object EditDeviceScreen: Screen("edit_device_screen")
    object LoginScreen: Screen("login_scree")
    object RegisterScreen: Screen("register_screen")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}