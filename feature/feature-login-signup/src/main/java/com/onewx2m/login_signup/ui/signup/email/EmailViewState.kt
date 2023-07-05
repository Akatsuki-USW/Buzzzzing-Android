package com.onewx2m.login_signup.ui.signup.email

import com.onewx2m.design_system.R
import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
import com.onewx2m.mvi.ViewState

data class EmailViewState(
    val emailLayoutState: TextInputLayoutState = TextInputLayoutState.INACTIVE,
    val emailLayoutHelperTextResId: Int = R.string.word_space,
) : ViewState
