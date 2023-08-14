package com.onewx2m.core_ui.model

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavigationParcel(
    val bundle: Bundle = bundleOf(),
    val destination: Int = -1,
) : Parcelable