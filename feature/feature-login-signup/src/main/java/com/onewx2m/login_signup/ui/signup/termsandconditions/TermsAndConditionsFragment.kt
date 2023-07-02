package com.onewx2m.login_signup.ui.signup.termsandconditions

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import com.onewx2m.feature_login_signup.databinding.FragmentTermsAndConditionsBinding
import com.onewx2m.login_signup.ui.signup.SignUpViewModel
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsAndConditionsFragment :
    MviFragment<FragmentTermsAndConditionsBinding, TermsAndConditionsViewState, TermsAndConditionsEvent, TermsAndConditionsSideEffect, TermsAndConditionsViewModel>(
        FragmentTermsAndConditionsBinding::inflate,
    ) {
    override val viewModel: TermsAndConditionsViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    private var isOnResumed: Boolean = false

    override fun onResume() {
        super.onResume()

        if (isOnResumed) viewModel.postReRenderSideEvent()
        isOnResumed = true
    }

    override fun initView() {
        binding.apply {
            checkBoxAgreeAll.setOnCheckedChangeListener { _, isChecked ->
                viewModel.changeAgreeAllCheckboxState(isChecked)
            }

            layoutPersonalInformationAgree.apply {
                onThrottleClick {
                    viewModel.goToPersonalInformationHandlingPolicyWebSite()
                }
                checkboxChangeListener {
                    viewModel.changePersonalHandlingPolicyState(it)
                }
            }

            layoutTermsAndConditionsAgree.apply {
                onThrottleClick {
                    viewModel.goToTermsAndConditionsWebSite()
                }
                checkboxChangeListener {
                    viewModel.changeTermsAndConditionsCheckboxState(it)
                }
            }

            layoutOver14Agree.apply {
                checkboxChangeListener {
                    viewModel.changeOver14CheckboxState(it)
                }
            }
        }
    }

    override fun render(current: TermsAndConditionsViewState) {
        super.render(current)

        binding.checkBoxAgreeAll.isChecked = current.isChildrenItemsAllChecked
        binding.layoutPersonalInformationAgree.isChecked = current.isPersonalHandlingPolicyChecked
        binding.layoutTermsAndConditionsAgree.isChecked = current.isTermsAndConditionsChecked
        binding.layoutOver14Agree.isChecked = current.isOver14Checked

        viewModel.changeSignUpButtonState(current)
    }

    override fun handleSideEffect(sideEffect: TermsAndConditionsSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            TermsAndConditionsSideEffect.GoToPersonalInformationHandlingPolicyWebSite -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(com.onewx2m.core_ui.R.string.url_personal_information_handling_policy)))
                startActivity(intent)
            }
            TermsAndConditionsSideEffect.GoToTermsAndConditionsWebSite -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(com.onewx2m.core_ui.R.string.url_terms_and_conditions)))
                startActivity(intent)
            }

            is TermsAndConditionsSideEffect.ChangeSignUpButtonState -> {
                parentViewModel.postChangeMainButtonStateEvent(sideEffect.signUpButtonState)
            }

            TermsAndConditionsSideEffect.DoReRender -> render(viewModel.state.value)
        }
    }
}
