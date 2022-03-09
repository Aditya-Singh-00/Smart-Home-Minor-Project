package com.aditya.smarthouse.ui.screens.edit_device

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.aditya.smarthouse.data.icons
import com.aditya.smarthouse.repository.SmartHouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditDeviceViewModel @Inject constructor(
    private val smartHouseRepository: SmartHouseRepository
) : ViewModel() {

    val deviceIcons = icons

    private val _deviceIcon = mutableStateOf(icons[0])
    val deviceIcon: State<Int> = _deviceIcon

    private val _deviceNameText = mutableStateOf("")
    val deviceNameText: State<String> = _deviceNameText

    fun setDeviceIcon(value: Int) {
        _deviceIcon.value = value
    }

    fun setDeviceNameText(value: String) {
        _deviceNameText.value = value
    }

    fun setDevice(id: Int) {
        smartHouseRepository.setDeviceConfig(
            id = id.toString(),
            name = deviceNameText.value,
            vector = deviceIcon.value
        )
    }
}