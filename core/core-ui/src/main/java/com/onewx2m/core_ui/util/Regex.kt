package com.onewx2m.core_ui.util

object Regex {
    const val NICKNAME_CHAR = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]*$"
    const val NICKNAME_LENGTH = "^.{2,8}$"
    const val EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
}
