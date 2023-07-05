package com.onewx2m.login_signup.ui.signup.email

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onewx2m.core_ui.extensions.textChangesToFlow
import com.onewx2m.core_ui.util.Constants
import com.onewx2m.core_ui.util.PermissionManager
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_login_signup.databinding.FragmentEmailBinding
import com.onewx2m.login_signup.ui.signup.SignUpViewModel
import com.onewx2m.login_signup.ui.signup.profileandnickname.ProfileAndNicknameSideEffect
import com.onewx2m.mvi.MviFragment
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class EmailFragment :
    MviFragment<FragmentEmailBinding, EmailViewState, EmailEvent, EmailSideEffect, EmailViewModel>(
        FragmentEmailBinding::inflate,
    ) {
    override val viewModel: EmailViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        observeTextInputLayoutEmail()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeTextInputLayoutEmail() {
        /**
         *
         * ViewPager2에서 사용자가 다른 화면에 진입했다가 다시 돌아오는 경우 닉네임 중복 검사를 다시 실행하기 위해 repeatOnStarted를 사용하지 않음
         */
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                binding.textInputLayoutEmail.editText.textChangesToFlow()
                    .map { email ->
                        viewModel.postEmailStateNormalOrInactiveEvent(binding.textInputLayoutEmail.editText.isFocused)
                        viewModel.postSignUpButtonStateDisableSideEffect()
                        email
                    }
                    .debounce(Constants.EMAIL_INPUT_DEBOUNCE)
                    .onEach { email ->
                        viewModel.checkEmailRegexAndUpdateUi(
                            email,
                            binding.textInputLayoutEmail.editText.isFocused,
                        )
                    }
                    .launchIn(this)
            }
        }
    }

    override fun render(current: EmailViewState) {
        super.render(current)

        binding.apply {
            textInputLayoutEmail.state = current.emailLayoutState
            textInputLayoutEmail.helperText = getString(current.emailLayoutHelperTextResId)
        }
    }

    override fun handleSideEffect(sideEffect: EmailSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            is EmailSideEffect.ChangeSignUpButtonState -> parentViewModel.postChangeMainButtonStateEvent(
                sideEffect.buttonState,
            )

            is EmailSideEffect.UpdateSignUpEmail -> parentViewModel.email = sideEffect.email
        }
    }
}
