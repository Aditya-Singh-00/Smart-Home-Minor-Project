package com.aditya.smarthouse.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aditya.smarthouse.R
import com.aditya.smarthouse.ui.theme.spaceMedium

@ExperimentalAnimationApi
@Composable
fun ControllerHeader(
    greeting: String = "",
    username: String = "",
    onLogoutIconClicked: () -> Unit
) {

    val greetingName = username.substringBefore(" ")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_smarthome),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.70f)
                .height(200.dp)
                .align(Alignment.CenterStart),
            contentScale = ContentScale.Crop
        )
        Image(
            imageVector = Icons.Default.Logout,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(spaceMedium)
                .clickable(onClick = { onLogoutIconClicked() })
        )
        Column(
            modifier = Modifier
                .padding(spaceMedium)
                .fillMaxWidth(0.50f)
                .align(Alignment.BottomEnd),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = greeting,
                style = MaterialTheme.typography.body1,
                fontSize = 18.sp
            )
            Text(
                text = greetingName,
                style = MaterialTheme.typography.body1,
                fontSize = 22.sp
            )
        }
    }
}