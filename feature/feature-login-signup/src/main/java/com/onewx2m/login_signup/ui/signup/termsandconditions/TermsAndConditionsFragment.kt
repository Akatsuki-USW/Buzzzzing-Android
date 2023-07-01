package com.onewx2m.login_signup.ui.signup.termsandconditions

import androidx.fragment.app.viewModels
import com.onewx2m.feature_login_signup.databinding.FragmentTermsAndConditionsBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsAndConditionsFragment :
    MviFragment<FragmentTermsAndConditionsBinding, TermsAndConditionsViewState, TermsAndConditionsEvent, TermsAndConditionsSideEffect, TermsAndConditionsViewModel>(
        FragmentTermsAndConditionsBinding::inflate,
    ) {
    override val viewModel: TermsAndConditionsViewModel by viewModels()

    override fun initView() {
    }
}
