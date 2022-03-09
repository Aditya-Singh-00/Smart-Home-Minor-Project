package com.aditya.smarthouse.util

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.aditya.smarthouse.ui.screens.controller.ControllerScreen
import com.aditya.smarthouse.ui.screens.edit_device.EditDeviceScreen
import com.aditya.smarthouse.ui.screens.login.LoginScreen
import com.aditya.smarthouse.ui.screens.register.RegisterScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(
                route = Screen.LoginScreen.route
            ) {
                LoginScreen(navController = navController)
            }
            composable(Screen.RegisterScreen.route) {
                RegisterScreen(navController = navController)
            }
            composable(
                route = Screen.ControllerScreen.route,
                exitTransition = { _, _ ->
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(animationSpec = tween(300))
                },
                popEnterTransition = { _, _ ->
                    slideInHorizontally(
                        initialOffsetX = { -300 },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(animationSpec = tween(300))
                }

            ) {
                ControllerScreen(navController = navController)
            }
            composable(
                route = Screen.EditDeviceScreen.route + "/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.StringType }
                ),
                enterTransition = { _, _ ->
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(animationSpec = tween(300))
                },
                popExitTransition = { _, _ ->
                    slideOutHorizontally(
                        targetOffsetX = { 300 },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) { entry ->
                val id = entry.arguments?.getString("id")?.toInt()

                id?.let {
                    EditDeviceScreen(
                        navController = navController,
                        id = id
                    )
                }
            }
        }
    }
}