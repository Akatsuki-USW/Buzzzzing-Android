package com.onewx2m.login_signup.ui.signup.termsandconditions

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsAndConditionsViewModel @Inject constructor() :
    MviViewModel<TermsAndConditionsViewState, TermsAndConditionsEvent, TermsAndConditionsSideEffect>(TermsAndConditionsViewState()) {
    override fun reduceState(
        current: TermsAndConditionsViewState,
        event: TermsAndConditionsEvent,
    ): TermsAndConditionsViewState {
        TODO("Not yet implemented")
    }
}
