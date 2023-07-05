package com.onewx2m.login_signup.ui.signup.email

import androidx.fragment.app.viewModels
import com.onewx2m.feature_login_signup.databinding.FragmentEmailBinding
import com.onewx2m.mvi.MviFragment

class EmailFragment :
    MviFragment<FragmentEmailBinding, EmailViewState, EmailEvent, EmailSideEffect, EmailViewModel>(
        FragmentEmailBinding::inflate,
    ) {
    override val viewModel: EmailViewModel by viewModels()

    override fun initView() {

    }
}
