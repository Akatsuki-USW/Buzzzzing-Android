package com.onewx2m.login_signup.ui.signup.termsandconditions

import com.onewx2m.mvi.Event

sealed interface TermsAndConditionsEvent : Event {
    data class ChangeAgreeAllCheckboxState(val isChecked: Boolean) : TermsAndConditionsEvent
    data class ChangePersonalHandlingPolicyCheckboxState(val isChecked: Boolean) :
        TermsAndConditionsEvent

    data class ChangeTermsAndConditionsCheckboxState(val isChecked: Boolean) :
        TermsAndConditionsEvent

    data class ChangeOver14CheckboxState(val isChecked: Boolean) : TermsAndConditionsEvent
}
