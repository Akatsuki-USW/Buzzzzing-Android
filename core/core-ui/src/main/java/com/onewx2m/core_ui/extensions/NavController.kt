package com.onewx2m.core_ui.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.navigateActionWithDefaultAnim(action: NavDirections, popUpToId: Int = -1, inclusive: Boolean = false) {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(popUpToId, inclusive)
        .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
        .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
        .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        .build()

    navigate(action, navOptions)
}