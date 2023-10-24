package com.onewx2m.feature_myinfo.ui.editmyinfo

import android.net.Uri
import com.onewx2m.design_system.R
import com.onewx2m.design_system.components.button.MainButtonType
import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
import com.onewx2m.mvi.ViewState

data class EditMyInfoViewState(
    val nicknameLayoutState: TextInputLayoutState = TextInputLayoutState.INACTIVE,
    val nicknameLayoutHelperTextResId: Int = com.onewx2m.core_ui.R.string.text_input_layout_nickname_helper_hint,
    val profileUri: Uri? = null,
    val emailLayoutState: TextInputLayoutState = TextInputLayoutState.INACTIVE,
    val emailLayoutHelperTextResId: Int = R.string.word_space,
    val mainButtonType: MainButtonType = MainButtonType.DISABLE,
    val isScrollViewVisible: Boolean = true,
    val isLottieVisible: Boolean = false,
) : ViewState
