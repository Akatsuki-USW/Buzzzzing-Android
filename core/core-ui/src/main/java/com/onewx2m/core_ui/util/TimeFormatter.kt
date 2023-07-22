package com.onewx2m.core_ui.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

object TimeFormatter {
    private const val ahLocaleKorean = "a h시"

    private val ahLocaleKoreanFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern(ahLocaleKorean)

    fun getahLocaleKorean(hour: Int): String {
        return ahLocaleKoreanFormatter.format(LocalTime.of(hour, 0))
    }
}
