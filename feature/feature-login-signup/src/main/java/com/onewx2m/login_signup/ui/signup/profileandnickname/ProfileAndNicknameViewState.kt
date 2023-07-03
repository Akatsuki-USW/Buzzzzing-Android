package com.onewx2m.login_signup.ui.signup.profileandnickname

import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
import com.onewx2m.feature_login_signup.R
import com.onewx2m.mvi.ViewState

data class ProfileAndNicknameViewState(
    val nickname: String = "",
    val nicknameLayoutState: TextInputLayoutState = TextInputLayoutState.INACTIVE,
    val nicknameLayoutHelperTextResId: Int = com.onewx2m.core_ui.R.string.text_input_layout_nickname_helper_hint
) : ViewState
