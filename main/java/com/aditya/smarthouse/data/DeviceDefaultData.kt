package com.aditya.smarthouse.data

import com.aditya.smarthouse.domain.models.Device
import com.aditya.smarthouse.util.OFF

val devices = listOf(
    Device(
        id = 0,
        vector = icons[0],
        name = "Device 1",
        status = OFF
    ),
    Device(
        id = 1,
        vector = icons[1],
        name = "Device 2",
        status = OFF
    ),
    Device(
        id = 2,
        vector = icons[2],
        name = "Device 3",
        status = OFF
    ),
    Device(
        id = 3,
        vector = icons[6],
        name = "Device 4",
        status = OFF
    )
)