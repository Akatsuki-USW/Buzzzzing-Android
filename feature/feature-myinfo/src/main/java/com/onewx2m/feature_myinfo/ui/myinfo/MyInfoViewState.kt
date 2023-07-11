package com.onewx2m.feature_myinfo.ui.myinfo

import com.onewx2m.core_ui.util.BuzzzzingUser
import com.onewx2m.mvi.ViewState

data class MyInfoViewState(
    val nickname: String = BuzzzzingUser.nickname,
    val profileUrl: String = BuzzzzingUser.profileImageUrl,
) : ViewState
