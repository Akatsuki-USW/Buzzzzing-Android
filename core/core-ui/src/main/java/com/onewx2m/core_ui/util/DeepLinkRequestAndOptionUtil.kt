package com.onewx2m.core_ui.util

import android.content.Context
import android.net.Uri
import android.provider.Settings.Global.getString
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.onewx2m.core_ui.R
import com.onewx2m.core_ui.model.WriteContent
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.nio.charset.StandardCharsets
import java.util.Base64

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
    fun getRecommendPlaceRequestAndOption(
        context: Context,
        popUpToId: Int = -1,
        inclusive: Boolean = false,
    ): Pair<NavDeepLinkRequest, NavOptions> {
        return getNaviRequestAndOption(
            context.getString(R.string.deeplink_recommend_place_fragment).toUri(),
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
        val writeContentJsonString = Base64.getEncoder().encodeToString(Json.encodeToString(writeContent).toByteArray())
        val deepLinkString = context.getString(R.string.deeplink_write_fragment).replace("{writeContent}", writeContentJsonString)
        Timber.tag("테스트").d("$deepLinkString")
        return getNaviRequestAndOption(
            deepLinkString.toUri(),
        )
    }

    fun getLocationDetailRequestAndOption(
        context: Context,
        locationId: Int = -1,
    ): Pair<NavDeepLinkRequest, NavOptions> {
        val deepLinkString = context.getString(R.string.deeplink_location_detail_fragment).replace("{locationId}", "$locationId")
        return getNaviRequestAndOption(
            deepLinkString.toUri(),
        )
    }

    fun getSpotDetailRequestAndOption(
        context: Context,
        spotId: Int = -1,
    ): Pair<NavDeepLinkRequest, NavOptions> {
        val deepLinkString = context.getString(R.string.deeplink_spot_detail_fragment).replace("{spotId}", "$spotId")
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
