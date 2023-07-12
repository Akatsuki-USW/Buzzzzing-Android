package com.onewx2m.feature_myinfo.ui.editmyinfo

import androidx.fragment.app.viewModels
import com.onewx2m.feature_myinfo.databinding.FragmentEditMyInfoBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditMyInfoFragment :
    MviFragment<FragmentEditMyInfoBinding, EditMyInfoViewState, EditMyInfoEvent, EditMyInfoSideEffect, EditMyInfoViewModel>(
        FragmentEditMyInfoBinding::inflate,
    ) {
    override val viewModel: EditMyInfoViewModel by viewModels()

    override fun initView() {
    }
}
