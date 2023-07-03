package com.onewx2m.login_signup.ui.signup.profileandnickname

import android.view.View
import androidx.fragment.app.viewModels
import com.onewx2m.core_ui.extensions.textChangesToFlow
import com.onewx2m.core_ui.util.Constants
import com.onewx2m.feature_login_signup.databinding.FragmentProfileAndNicknameBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class ProfileAndNicknameFragment :
    MviFragment<FragmentProfileAndNicknameBinding, ProfileAndNicknameViewState, ProfileAndNicknameEvent, ProfileAndNicknameSideEffect, ProfileAndNicknameViewModel>(
        FragmentProfileAndNicknameBinding::inflate,
    ) {
    override val viewModel: ProfileAndNicknameViewModel by viewModels()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun initView() {
        binding.apply {
            textInputLayoutNickname.apply {
                doGetFocus = {
                    viewModel.postScrollToKeyboardHeightSideEffect()
                }
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            binding.textInputLayoutNickname.editText.textChangesToFlow()
                .filter { nickname ->
                    viewModel.checkNicknameRegexAndUpdateUi(nickname)
                }.debounce(Constants.NICKNAME_INPUT_DEBOUNCE)
                .onEach {
                    Timber.d("$it")
                }
                .launchIn(this)
        }
    }

    override fun render(current: ProfileAndNicknameViewState) {
        super.render(current)

        binding.textInputLayoutNickname.apply {
            state = current.nicknameLayoutState
            helperText = getString(current.nicknameLayoutHelperTextResId)
        }
    }

    override fun handleSideEffect(sideEffect: ProfileAndNicknameSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            ProfileAndNicknameSideEffect.ScrollToKeyBoardHeight -> {
                binding.scrollView.apply {
                    smoothScrollTo(scrollX, Constants.NORMAL_KEYBOARD_HEIGHT)
                }
            }
        }
    }
}
