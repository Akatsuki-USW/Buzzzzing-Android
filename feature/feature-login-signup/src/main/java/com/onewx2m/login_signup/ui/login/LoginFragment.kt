package com.onewx2m.login_signup.ui.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.onewx2m.core_ui.extensions.navigateActionWithDefaultAnim
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_login_signup.R
import com.onewx2m.feature_login_signup.databinding.FragmentLoginBinding
import com.onewx2m.login_signup.util.KakaoLoginUtil
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    MviFragment<FragmentLoginBinding, LoginViewState, LoginEvent, LoginSideEffect, LoginViewModel>(
        FragmentLoginBinding::inflate,
    ) {
    override val viewModel: LoginViewModel by viewModels()

    private val kakaoLoginUtil: KakaoLoginUtil by lazy {
        KakaoLoginUtil()
    }

    override fun initView() {
        binding.buttonKakao.onThrottleClick {
            viewModel.onClickKakaoLoginButton()
        }
    }

    override fun render(current: LoginViewState) {
        binding.buttonKakao.state = current.kakaoLoginButtonState
    }

    override fun handleSideEffect(sideEffect: LoginSideEffect) = when (sideEffect) {
        LoginSideEffect.TryLoginByKakao -> kakaoLogin()
        LoginSideEffect.GoToHomeFragment -> goToHomeFragment()
        is LoginSideEffect.GoToSignUpFragment -> goToSignUpFragment(sideEffect.signToken)
        is LoginSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.message)
            .show()
    }

    private fun kakaoLogin() {
        kakaoLoginUtil.kakaoLogin(
            requireContext(),
            onSuccess = { token -> viewModel.handleKakaoLoginSuccess(token) },
            onFail = { error -> viewModel.handleKakaoLoginFail() },
        )
    }

    private fun goToHomeFragment() {
        val (request, navOptions) = DeepLinkUtil.getHomeRequestAndOption(
            requireContext(),
            R.id.loginFragment,
            true,
        )
        findNavController().navigate(request, navOptions)
    }

    private fun goToSignUpFragment(signToken: String) {
        val action = LoginFragmentDirections.actionLoginToSignup(signToken)
        findNavController().navigateActionWithDefaultAnim(action)
    }
}
