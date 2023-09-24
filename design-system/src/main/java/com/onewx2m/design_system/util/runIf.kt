package com.onewx2m.design_system.util

internal inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
    run()
} else {
    this
}
