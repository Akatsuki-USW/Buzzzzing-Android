package com.onewx2m.design_system.components.button

import androidx.compose.ui.graphics.Color
import com.onewx2m.design_system.theme.BLACK01
import com.onewx2m.design_system.theme.BLUE
import com.onewx2m.design_system.theme.BLUE_LIGHT
import com.onewx2m.design_system.theme.GRAY03
import com.onewx2m.design_system.theme.GRAY05
import com.onewx2m.design_system.theme.GRAY06
import com.onewx2m.design_system.theme.KAKAO
import com.onewx2m.design_system.theme.KAKAO_LOADING
import com.onewx2m.design_system.theme.WHITE01
import com.onewx2m.design_system.theme.WHITE02

data class ButtonColorScheme(
    val text: Color,
    val background: Color,
)

enum class ButtonColor(
    val positiveColorScheme: ButtonColorScheme,
    val negativeColorScheme: ButtonColorScheme,
    val disableColorScheme: ButtonColorScheme,
    val loadingColorScheme: ButtonColorScheme,
) {
    Main(
        positiveColorScheme = ButtonColorScheme(
            text = WHITE01,
            background = BLUE,
        ),
        negativeColorScheme = ButtonColorScheme(
            text = WHITE01,
            background = BLUE_LIGHT,
        ),
        disableColorScheme = ButtonColorScheme(
            text = GRAY03,
            background = GRAY06,
        ),
        loadingColorScheme = ButtonColorScheme(
            text = WHITE01,
            background = BLUE_LIGHT,
        ),
    ),
    KakaoLogin(
        positiveColorScheme = ButtonColorScheme(
            text = BLACK01,
            background = KAKAO,
        ),
        negativeColorScheme = ButtonColorScheme(
            text = BLACK01,
            background = KAKAO,
        ),
        disableColorScheme = ButtonColorScheme(
            text = BLACK01,
            background = KAKAO,
        ),
        loadingColorScheme = ButtonColorScheme(
            text = WHITE02,
            background = WHITE02,
        ),
    ),
}
