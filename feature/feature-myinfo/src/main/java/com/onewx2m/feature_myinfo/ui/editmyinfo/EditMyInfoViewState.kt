package com.onewx2m.feature_myinfo.ui.editmyinfo

import android.net.Uri
import com.onewx2m.core_ui.util.BuzzzzingUser
import com.onewx2m.mvi.ViewState

data class EditMyInfoViewState(
    val profileImageUrl: String = BuzzzzingUser.profileImageUrl,
    val nickname: String = BuzzzzingUser.nickname,
    val email: String = BuzzzzingUser.email,
    val profileUri: Uri? = null
) : ViewState
