package com.onewx2m.login_signup.ui.signup

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
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

    private val viewPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.viewPagerIndicator.rating = (position + 1).toFloat()
        }
    }

    override fun initView() {
        binding.apply {
            viewPagerSingUp.apply {
                isUserInputEnabled = true
                adapter = pagerAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.viewPagerSingUp.registerOnPageChangeCallback(viewPageChangeCallback)
    }

    override fun onStop() {
        super.onStop()

        binding.viewPagerSingUp.unregisterOnPageChangeCallback(viewPageChangeCallback)
    }
}
