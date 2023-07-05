package com.onewx2m.login_signup.ui.signup

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.onewx2m.core_ui.extensions.hideKeyboard
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_login_signup.databinding.FragmentSignUpBinding
import com.onewx2m.login_signup.ui.signup.adapter.SignUpFragmentStateAdapter
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment :
    MviFragment<FragmentSignUpBinding, SignUpViewState, SignUpEvent, SignUpSideEffect, SignUpViewModel>(
        FragmentSignUpBinding::inflate,
    ) {
    override val viewModel: SignUpViewModel by viewModels()
    private val pagerAdapter: SignUpFragmentStateAdapter by lazy {
        SignUpFragmentStateAdapter(this)
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.onBackPressed()
        }
    }

    override fun initView() {
        binding.apply {
            viewPagerSignUp.apply {
                isUserInputEnabled = false
                adapter = pagerAdapter
            }

            buttonSignUp.onThrottleClick {
                viewModel.onClickMainButton()
            }

            imageButtonBack.onThrottleClick {
                viewModel.onBackPressed()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedDispatcher)
    }

    override fun render(current: SignUpViewState) {
        super.render(current)

        binding.buttonSignUp.apply {
            state = current.mainButtonState
            text = getString(current.buttonTextRes)
        }
        binding.viewPagerSignUp.apply {
            setCurrentItem(current.viewPagerPosition, true)
            visibility = if (current.isViewPagerVisible) View.VISIBLE else View.INVISIBLE
        }
        binding.lottieSigningUp.visibility =
            if (current.isLottieVisible) View.VISIBLE else View.INVISIBLE

        binding.viewPagerIndicator.rating = current.viewPagerPosition.toFloat() + 1
    }

    override fun handleSideEffect(sideEffect: SignUpSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            SignUpSideEffect.GoToPrevPage -> findNavController().popBackStack()
            SignUpSideEffect.HideKeyboard -> hideKeyboard()
            SignUpSideEffect.PlayLottie -> binding.lottieSigningUp.playAnimation()
            SignUpSideEffect.StopLottie -> binding.lottieSigningUp.cancelAnimation()
            is SignUpSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg).show()
        }
    }
}
