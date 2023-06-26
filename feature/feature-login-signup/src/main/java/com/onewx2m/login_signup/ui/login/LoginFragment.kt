package com.onewx2m.login_signup.ui.login

import androidx.fragment.app.viewModels
import com.onewx2m.feature_login_signup.databinding.FragmentLoginBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : MviFragment<FragmentLoginBinding, LoginViewState, LoginEvent, LoginSideEffect, LoginViewModel>(
    FragmentLoginBinding::inflate,
) {
    override val viewModel: LoginViewModel by viewModels()

    override fun initView() {
        binding.buttonKakao.onThrottleClick {
            viewModel.onClickKakaoLoginButton()
        }
    }

    override fun render(current: LoginViewState) {
        binding.buttonKakao.state = current.kakaoLoginButtonState
    }

    override fun handleSideEffect(sideEffect: LoginSideEffect) {
    }
}
