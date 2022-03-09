package com.aditya.smarthouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smarthouse.ui.theme.SmartHouseTheme
import com.aditya.smarthouse.util.Navigation
import com.aditya.smarthouse.util.Screen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHouseTheme {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    val navController = rememberAnimatedNavController()
                    mainViewModel.userLoggedIn.value?.let { loggedIn ->
                        if(loggedIn) {
                            Navigation(
                                navController = navController,
                                startDestination = Screen.ControllerScreen.route
                            )
                        } else {
                            Navigation(
                                navController = navController,
                                startDestination = Screen.LoginScreen.route
                            )
                        }
                    }
                }
            }
        }
    }
}


