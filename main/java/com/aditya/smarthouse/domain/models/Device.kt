package com.aditya.smarthouse.domain.models

data class Device(
    val id: Int = 0,
    var vector: Int = 0,
    var name: String = "",
    var status: Int = 0
)