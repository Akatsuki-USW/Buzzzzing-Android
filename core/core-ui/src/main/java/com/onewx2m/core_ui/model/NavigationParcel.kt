package com.onewx2m.core_ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavigationParcel(
    val type: String = "",
    val targetId: Int = -1,
    val destination: Int = -1,
) : Parcelable
