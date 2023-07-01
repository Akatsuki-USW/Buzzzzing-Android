package com.onewx2m.login_signup.ui.signup.termsandconditions

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsAndConditionsViewModel @Inject constructor() :
    MviViewModel<TermsAndConditionsViewState, TermsAndConditionsEvent, TermsAndConditionsSideEffect>(
        TermsAndConditionsViewState(),
    ) {
    override fun reduceState(
        current: TermsAndConditionsViewState,
        event: TermsAndConditionsEvent,
    ): TermsAndConditionsViewState = when (event) {
        is TermsAndConditionsEvent.ChangeAgreeAllCheckboxState -> {
            if (event.isChecked) {
                getChildAgreementItemsStateTo(current, true)
            } else if (current.isChildrenItemsAllChecked) {
                getChildAgreementItemsStateTo(current, false)
            } else {
                current
            }
        }
        is TermsAndConditionsEvent.ChangeOver14CheckboxState -> current.copy(isOver14Checked = event.isChecked)

        is TermsAndConditionsEvent.ChangePersonalHandlingPolicyCheckboxState -> current.copy(isPersonalHandlingPolicyChecked = event.isChecked)

        is TermsAndConditionsEvent.ChangeTermsAndConditionsCheckboxState -> current.copy(isTermsAndConditionsChecked = event.isChecked)
    }

    private fun getChildAgreementItemsStateTo(viewState: TermsAndConditionsViewState, isChecked: Boolean) = viewState.copy(
        isOver14Checked = isChecked,
        isTermsAndConditionsChecked = isChecked,
        isPersonalHandlingPolicyChecked = isChecked,
    )

    fun changeAgreeAllCheckboxState(isChecked: Boolean) {
        postEvent(TermsAndConditionsEvent.ChangeAgreeAllCheckboxState(isChecked))
    }

    fun changePersonalHandlingPolicyState(isChecked: Boolean) {
        postEvent(TermsAndConditionsEvent.ChangePersonalHandlingPolicyCheckboxState(isChecked))
    }

    fun changeOver14CheckboxState(isChecked: Boolean) {
        postEvent(TermsAndConditionsEvent.ChangeOver14CheckboxState(isChecked))
    }

    fun changeTermsAndConditionsCheckboxState(isChecked: Boolean) {
        postEvent(TermsAndConditionsEvent.ChangeTermsAndConditionsCheckboxState(isChecked))
    }
}
