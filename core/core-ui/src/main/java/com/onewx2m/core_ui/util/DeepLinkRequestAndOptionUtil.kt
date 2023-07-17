package com.onewx2m.core_ui.util

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.onewx2m.core_ui.R

object DeepLinkUtil {
    fun getHomeRequestAndOption(
        context: Context,
        popUpToId: Int = -1,
        inclusive: Boolean = false,
    ): Pair<NavDeepLinkRequest, NavOptions> {
        return getNaviRequestAndOption(
            context,
            R.string.deeplink_home_fragment,
            popUpToId,
            inclusive,
        )
    }

    fun getLoginRequestAndOption(
        context: Context,
        popUpToId: Int = -1,
        inclusive: Boolean = false,
    ): Pair<NavDeepLinkRequest, NavOptions> {
        return getNaviRequestAndOption(
            context,
            R.string.deeplink_login_fragment,
            popUpToId,
            inclusive,
        )
    }

    private fun getNaviRequestAndOption(
        context: Context,
        destination: Int,
        popUpToId: Int = -1,
        inclusive: Boolean = false,
    ): Pair<NavDeepLinkRequest, NavOptions> {
        val request = NavDeepLinkRequest.Builder
            .fromUri(context.getString(destination).toUri())
            .build()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(popUpToId, inclusive)
            .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .build()

        return (request to navOptions)
    }
}
