package com.onewx2m.core_ui.util

import android.content.Context
import android.net.Uri
import android.provider.Settings.Global.getString
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.onewx2m.core_ui.R
import com.onewx2m.core_ui.model.WriteContent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object DeepLinkUtil {
    fun getHomeRequestAndOption(
        context: Context,
        popUpToId: Int = -1,
        inclusive: Boolean = false,
    ): Pair<NavDeepLinkRequest, NavOptions> {
        return getNaviRequestAndOption(
            context.getString(R.string.deeplink_home_fragment).toUri(),
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
            context.getString(R.string.deeplink_login_fragment).toUri(),
            popUpToId,
            inclusive,
        )
    }

    fun getWriteRequestAndOption(
        context: Context,
        writeContent: WriteContent = WriteContent(),
    ): Pair<NavDeepLinkRequest, NavOptions> {
        val writeContentJsonString = Json.encodeToString(writeContent)
        val deepLinkString = context.getString(R.string.deeplink_write_fragment).replace("{writeContent}", writeContentJsonString)
        return getNaviRequestAndOption(
            deepLinkString.toUri(),
        )
    }

    private fun getNaviRequestAndOption(
        destination: Uri,
        popUpToId: Int = -1,
        inclusive: Boolean = false,
    ): Pair<NavDeepLinkRequest, NavOptions> {
        val request = NavDeepLinkRequest.Builder
            .fromUri(destination)
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
