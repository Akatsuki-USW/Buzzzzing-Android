package com.onewx2m.login_signup.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.design_system.components.snsLoginButton.SnsLoginButtonState
import com.onewx2m.feature_login_signup.R
import com.onewx2m.feature_login_signup.databinding.FragmentLoginBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : MviFragment<FragmentLoginBinding, LoginViewState, LoginEvent, LoginSideEffect, LoginViewModel>(
    FragmentLoginBinding::inflate
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