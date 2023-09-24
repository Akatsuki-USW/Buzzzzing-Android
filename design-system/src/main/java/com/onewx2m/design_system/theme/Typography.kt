package com.onewx2m.design_system.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.onewx2m.design_system.R

internal val notoSansFamily = FontFamily(
    Font(R.font.notosanskrbold, FontWeight.Bold),
    Font(R.font.notosanskrmedium, FontWeight.Medium),
    Font(R.font.notosanskrregular, FontWeight.Normal),
)

private val notoSansStyle = TextStyle(
    fontFamily = notoSansFamily,
    fontWeight = FontWeight.Normal,
    letterSpacing = (-0.03).sp,
)

internal val Typography = BuzzzzingTypography(
    header1 = notoSansStyle.copy(
        fontWeight = FontWeight.Medium,
        lineHeight = 32.sp,
        fontSize = 28.sp,
    ),
    header2 = notoSansStyle.copy(
        fontWeight = FontWeight.Medium,
        lineHeight = 28.sp,
        fontSize = 24.sp,
    ),
    header3 = notoSansStyle.copy(
        fontWeight = FontWeight.Medium,
        lineHeight = 22.sp,
        fontSize = 18.sp,
    ),
    header4 = notoSansStyle.copy(
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp,
        fontSize = 16.sp,
    ),
    header5 = notoSansStyle.copy(
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        fontSize = 16.sp,
    ),
    header6 = notoSansStyle.copy(
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        fontSize = 14.sp,
    ),
    body1 = notoSansStyle.copy(
        lineHeight = 20.sp,
        fontSize = 16.sp,
    ),
    body2 = notoSansStyle.copy(
        lineHeight = 19.sp,
        fontSize = 15.sp,
    ),
    body3 = notoSansStyle.copy(
        lineHeight = 18.sp,
        fontSize = 14.sp,
    ),
    body4 = notoSansStyle.copy(
        lineHeight = 14.sp,
        fontSize = 13.sp,
    ),
    body5 = notoSansStyle.copy(
        fontWeight = FontWeight.Bold,
        lineHeight = 12.sp,
        fontSize = 10.sp,
    ),
    body6 = notoSansStyle.copy(
        lineHeight = 12.sp,
        fontSize = 10.sp,
    ),
    label1 = notoSansStyle.copy(
        lineHeight = 20.sp,
        fontSize = 16.sp,
    ),
    label2 = notoSansStyle.copy(
        lineHeight = 18.sp,
        fontSize = 14.sp,
    ),
    label3 = notoSansStyle.copy(
        lineHeight = 14.sp,
        fontSize = 12.sp,
    ),
    caption1 = notoSansStyle.copy(
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp,
        fontSize = 18.sp,
    ),
    caption2 = notoSansStyle.copy(
        lineHeight = 20.sp,
        fontSize = 18.sp,
    ),
    caption3 = notoSansStyle.copy(
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp,
        fontSize = 16.sp,
    ),
    caption4 = notoSansStyle.copy(
        lineHeight = 18.sp,
        fontSize = 16.sp,
    ),
)

@Immutable
data class BuzzzzingTypography(
    val header1: TextStyle,
    val header2: TextStyle,
    val header3: TextStyle,
    val header4: TextStyle,
    val header5: TextStyle,
    val header6: TextStyle,

    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val body5: TextStyle,
    val body6: TextStyle,

    val label1: TextStyle,
    val label2: TextStyle,
    val label3: TextStyle,

    val caption1: TextStyle,
    val caption2: TextStyle,
    val caption3: TextStyle,
    val caption4: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    BuzzzzingTypography(
        header1 = notoSansStyle,
        header2 = notoSansStyle,
        header3 = notoSansStyle,
        header4 = notoSansStyle,
        header5 = notoSansStyle,
        header6 = notoSansStyle,
        body1 = notoSansStyle,
        body2 = notoSansStyle,
        body3 = notoSansStyle,
        body4 = notoSansStyle,
        body5 = notoSansStyle,
        body6 = notoSansStyle,
        label1 = notoSansStyle,
        label2 = notoSansStyle,
        label3 = notoSansStyle,
        caption1 = notoSansStyle,
        caption2 = notoSansStyle,
        caption3 = notoSansStyle,
        caption4 = notoSansStyle,
    )
}
