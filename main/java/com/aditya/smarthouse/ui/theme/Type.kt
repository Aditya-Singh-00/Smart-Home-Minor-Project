package com.aditya.smarthouse.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aditya.smarthouse.R

// Set of Material typography styles to start with

val quicksand = FontFamily(
    Font(R.font.quicksand_light,FontWeight.Light),
    Font(R.font.quicksand_regular,FontWeight.Normal),
    Font(R.font.quicksand_medium,FontWeight.Medium),
    Font(R.font.quicksand_semibold,FontWeight.SemiBold),
    Font(R.font.quicksand_bold,FontWeight.Bold)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
)