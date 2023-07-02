package com.onewx2m.login_signup.ui.signup.termsandconditions

import com.onewx2m.design_system.components.button.MainButtonState
import com.onewx2m.mvi.SideEffect

sealed interface TermsAndConditionsSideEffect : SideEffect {
    object GoToPersonalInformationHandlingPolicyWebSite : TermsAndConditionsSideEffect
    object GoToTermsAndConditionsWebSite : TermsAndConditionsSideEffect
    data class ChangeSignUpButtonState(val signUpButtonState: MainButtonState) : TermsAndConditionsSideEffect
    object DoReRender : TermsAndConditionsSideEffect
}
