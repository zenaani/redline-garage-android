package com.zenaani.redlinegarage.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val BodoniModa = FontFamily(
    Font(
        resId = R.font.bodoni_moda,
        weight = FontWeight.Normal
    )
)

val Inter = FontFamily(
    Font(
        resId = R.font.inter,
        weight = FontWeight.Normal
    )
)

val AppTypography = Typography(
    displayLarge = TextStyle(fontFamily = Inter),
    displayMedium = TextStyle(fontFamily = Inter),
    displaySmall = TextStyle(fontFamily = Inter),

    headlineLarge = TextStyle(fontFamily = Inter),
    headlineMedium = TextStyle(fontFamily = Inter),
    headlineSmall = TextStyle(fontFamily = Inter),

    titleLarge = TextStyle(fontFamily = Inter),
    titleMedium = TextStyle(fontFamily = Inter),
    titleSmall = TextStyle(fontFamily = Inter),

    bodyLarge = TextStyle(fontFamily = Inter),
    bodyMedium = TextStyle(fontFamily = Inter),
    bodySmall = TextStyle(fontFamily = Inter),

    labelLarge = TextStyle(fontFamily = Inter),
    labelMedium = TextStyle(fontFamily = Inter),
    labelSmall = TextStyle(fontFamily = Inter)
)