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
        binding.apply {
            checkBoxAgreeAll.setOnCheckedChangeListener { _, isChecked ->
                viewModel.changeAgreeAllCheckboxState(isChecked)
            }

            layoutPersonalInformationAgree.checkboxChangeListener {
                viewModel.changePersonalHandlingPolicyState(it)
            }

            layoutOver14Agree.checkboxChangeListener {
                viewModel.changeOver14CheckboxState(it)
            }

            layoutTermsAndConditionsAgree.checkboxChangeListener {
                viewModel.changeTermsAndConditionsCheckboxState(it)
            }
        }
    }

    override fun render(current: TermsAndConditionsViewState) {
        super.render(current)

        binding.checkBoxAgreeAll.isChecked = current.isChildrenItemsAllChecked
        binding.layoutPersonalInformationAgree.isChecked = current.isPersonalHandlingPolicyChecked
        binding.layoutTermsAndConditionsAgree.isChecked = current.isTermsAndConditionsChecked
        binding.layoutOver14Agree.isChecked = current.isOver14Checked
    }
}
