package com.aditya.smarthouse.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aditya.smarthouse.domain.models.Device
import com.aditya.smarthouse.util.OFF
import com.aditya.smarthouse.util.ON

@ExperimentalAnimationApi
@Composable
fun DeviceCard(
    device: Device,
    onDeviceClick: () -> Unit,
    onEditDeviceClick: () -> Unit,
    onDeviceStatusChange: (Int, Int) -> Unit
) {

    val color = if (device.status == OFF) {
        MaterialTheme.colors.background
    } else {
        MaterialTheme.colors.primary
    }

    val text = if (device.status == OFF) "OFF" else "ON"

    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onDeviceClick)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = device.vector),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    elevation = 5.dp,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp)
                        .clickable(
                            onClick = {
                                if (device.status == OFF) {
                                    onDeviceStatusChange(device.id, ON)
                                } else {
                                    onDeviceStatusChange(device.id, OFF)
                                }
                            }
                        )
                ) {
                    Box(
                        modifier = Modifier.background(color)
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            IconButton(
                onClick = onEditDeviceClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Device"
                )
            }
        }
    }
}