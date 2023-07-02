package com.onewx2m.login_signup.ui.signup.profileandnickname

import androidx.fragment.app.viewModels
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
                    scrollView.smoothScrollTo(scrollView.scrollX, binding.imageViewProfile.top)
                }
            }
        }
    }
}
