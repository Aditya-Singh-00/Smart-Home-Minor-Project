package com.aditya.smarthouse.ui.screens.edit_device

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aditya.smarthouse.util.MAX_LENGTH

@ExperimentalFoundationApi
@Composable
fun EditDeviceScreen(
    navController: NavController,
    id: Int,
    editDeviceViewModel: EditDeviceViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Edit your device",
            modifier = Modifier
                .padding(start = 8.dp, top = 12.dp, bottom = 12.dp),
            style = MaterialTheme.typography.body1,
            fontSize = 32.sp
        )
        Card(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            shape = CircleShape,
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = editDeviceViewModel.deviceIcon.value),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp),
                    contentScale = ContentScale.Inside
                )
            }
        }
        OutlinedTextField(
            value = editDeviceViewModel.deviceNameText.value,
            onValueChange = {
                if (it.length <= MAX_LENGTH) {
                    editDeviceViewModel.setDeviceNameText(it)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = { Text(text = "Device Name") },
            singleLine = true,
        )
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .height(300.dp)
                .padding(8.dp)
        ) {
            items(items = editDeviceViewModel.deviceIcons) { icon ->
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(12.dp)
                        .clickable(
                            onClick = { editDeviceViewModel.setDeviceIcon(icon) }
                        ),
                    shape = CircleShape,
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.surface),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp),
                            contentScale = ContentScale.Inside
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                editDeviceViewModel.setDevice(id)
                navController.navigateUp()
            },
            modifier = Modifier
                .width(120.dp)
                .height(70.dp)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            shape = CircleShape,
            enabled = editDeviceViewModel.deviceNameText.value.isNotBlank()
        ) {
            Text(
                text = "Save",
                style = MaterialTheme.typography.body1
            )
        }
    }
}