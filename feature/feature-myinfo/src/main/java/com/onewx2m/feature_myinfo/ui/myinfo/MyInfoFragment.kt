package com.onewx2m.feature_myinfo.ui.myinfo

import androidx.fragment.app.viewModels
import com.onewx2m.feature_myinfo.databinding.FragmentMyInfoBinding
import com.onewx2m.mvi.MviFragment

class MyInfoFragment :
    MviFragment<FragmentMyInfoBinding, MyInfoViewState, MyInfoEvent, MyInfoSideEffect, MyInfoViewModel>(
        FragmentMyInfoBinding::inflate,
    ) {
    override val viewModel: MyInfoViewModel by viewModels()

    override fun initView() {

    }
}
