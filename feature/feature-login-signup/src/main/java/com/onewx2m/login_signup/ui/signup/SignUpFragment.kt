package com.onewx2m.login_signup.ui.signup

import androidx.fragment.app.viewModels
import com.onewx2m.feature_login_signup.databinding.FragmentSignUpBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : MviFragment<FragmentSignUpBinding, SignUpViewState, SignUpEvent, SignUpSideEffect, SignUpViewModel>(
    FragmentSignUpBinding::inflate,
) {
    override val viewModel: SignUpViewModel by viewModels()

    override fun initView() {
    }
}
