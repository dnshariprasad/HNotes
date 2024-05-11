package com.htrack.hnotes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.htrack.hnotes.R

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    )
)