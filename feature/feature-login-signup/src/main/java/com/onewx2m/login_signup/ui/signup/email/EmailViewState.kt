package com.onewx2m.login_signup.ui.signup.email

import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
import com.onewx2m.mvi.ViewState

data class EmailViewState(
    val email: String = "",
    val emailLayoutState: TextInputLayoutState = TextInputLayoutState.INACTIVE,
    val emailLayoutHelperTextResId: Int? = null,
) : ViewState
