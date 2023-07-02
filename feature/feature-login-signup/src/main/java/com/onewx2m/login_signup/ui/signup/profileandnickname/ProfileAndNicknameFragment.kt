package com.onewx2m.login_signup.ui.signup.profileandnickname

import androidx.fragment.app.viewModels
import com.onewx2m.core_ui.util.Constants
import com.onewx2m.feature_login_signup.databinding.FragmentProfileAndNicknameBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileAndNicknameFragment :
    MviFragment<FragmentProfileAndNicknameBinding, ProfileAndNicknameViewState, ProfileAndNicknameEvent, ProfileAndNicknameSideEffect, ProfileAndNicknameViewModel>(
        FragmentProfileAndNicknameBinding::inflate,
    ) {
    override val viewModel: ProfileAndNicknameViewModel by viewModels()

    override fun initView() {
        binding.apply {
            textInputLayoutNickname.apply {
                doGetFocus = {
                    viewModel.postScrollToKeyboardHeightSideEffect()
                }
            }
        }
    }

    override fun render(current: ProfileAndNicknameViewState) {
        super.render(current)
    }

    override fun handleSideEffect(sideEffect: ProfileAndNicknameSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            ProfileAndNicknameSideEffect.ScrollToKeyBoardHeight -> {
                binding.scrollView.apply {
                    smoothScrollTo(scrollX, scrollY + Constants.NORMAL_KEYBOARD_HEIGHT)
                }
            }
        }
    }
}
