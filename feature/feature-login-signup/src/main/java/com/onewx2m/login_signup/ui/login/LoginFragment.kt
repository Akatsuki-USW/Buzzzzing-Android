package com.onewx2m.login_signup.ui.login

import androidx.fragment.app.viewModels
import com.onewx2m.feature_login_signup.databinding.FragmentLoginBinding
import com.onewx2m.login_signup.util.KakaoLoginUtil
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : MviFragment<FragmentLoginBinding, LoginViewState, LoginEvent, LoginSideEffect, LoginViewModel>(
    FragmentLoginBinding::inflate,
) {
    override val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var kakaoLoginUtil: KakaoLoginUtil

    override fun initView() {
        binding.buttonKakao.onThrottleClick {
            viewModel.onClickKakaoLoginButton()
        }
    }

    override fun render(current: LoginViewState) {
        binding.buttonKakao.state = current.kakaoLoginButtonState
    }

    override fun handleSideEffect(sideEffect: LoginSideEffect) = when (sideEffect) {
        LoginSideEffect.TryLoginByKakao -> {
            kakaoLoginUtil.kakaoLogin(
                requireContext(),
                onSuccess = { token -> viewModel.handleKakaoLoginSuccess(token) },
                onFail = { error -> viewModel.handleKakaoLoginFail() },
            )
        }

        LoginSideEffect.GoToHomeFragment -> TODO()
        LoginSideEffect.GoToSignUpFragment -> TODO()
    }
}
