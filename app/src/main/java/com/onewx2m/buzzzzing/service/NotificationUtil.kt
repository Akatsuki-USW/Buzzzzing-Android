package com.onewx2m.buzzzzing.service

import com.onewx2m.core_ui.model.NavigationParcel
import com.onewx2m.recommend_place.ui.spotdetail.SpotDetailFragmentArgs

enum class NotificationType {
    CREATE_SPOT_COMMENT,
    CREATE_SPOT_COMMENT_COMMENT,
}

object NotificationUtil {

    fun getNavigationParcel(type: String, targetId: Int): NavigationParcel {
        return try {
            when (NotificationType.valueOf(type)) {
                NotificationType.CREATE_SPOT_COMMENT -> NavigationParcel(
                    SpotDetailFragmentArgs(spotId = targetId).toBundle(),
                    com.onewx2m.recommend_place.R.id.spotDetailFragment,
                )

                NotificationType.CREATE_SPOT_COMMENT_COMMENT -> NavigationParcel(
                    SpotDetailFragmentArgs(spotId = targetId).toBundle(),
                    com.onewx2m.recommend_place.R.id.spotDetailFragment,
                )
            }
        } catch (_: IllegalArgumentException) {
            NavigationParcel()
        }
    }
}
