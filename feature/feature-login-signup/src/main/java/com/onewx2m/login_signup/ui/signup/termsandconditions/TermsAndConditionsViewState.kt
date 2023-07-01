package com.onewx2m.login_signup.ui.signup.termsandconditions

import com.onewx2m.mvi.ViewState

data class TermsAndConditionsViewState(
    val isPersonalHandlingPolicyChecked: Boolean = false,
    val isTermsAndConditionsChecked: Boolean = false,
    val isOver14Checked: Boolean = false,
) : ViewState {
    val isChildrenItemsAllChecked
        get() = isPersonalHandlingPolicyChecked && isTermsAndConditionsChecked && isOver14Checked
}
