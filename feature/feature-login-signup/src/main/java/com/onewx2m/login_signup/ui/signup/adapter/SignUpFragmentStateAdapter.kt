package com.onewx2m.login_signup.ui.signup.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.onewx2m.login_signup.ui.signup.email.EmailFragment
import com.onewx2m.login_signup.ui.signup.profileandnickname.ProfileAndNicknameFragment
import com.onewx2m.login_signup.ui.signup.termsandconditions.TermsAndConditionsFragment

enum class SignUpFragmentType(val index: Int) {
    TERMS_CONDITION(0), PROFILE_NICKNAME(1), EMAIL(2)
}

class SignUpFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = SignUpFragmentType.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        SignUpFragmentType.TERMS_CONDITION.index -> TermsAndConditionsFragment()
        SignUpFragmentType.PROFILE_NICKNAME.index -> ProfileAndNicknameFragment()
        SignUpFragmentType.EMAIL.index -> EmailFragment()
        else -> throw IllegalAccessException()
    }
}
