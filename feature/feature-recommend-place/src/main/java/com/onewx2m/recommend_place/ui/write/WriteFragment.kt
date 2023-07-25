package com.onewx2m.recommend_place.ui.write

import androidx.fragment.app.viewModels
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.databinding.FragmentWriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteFragment :
    MviFragment<FragmentWriteBinding, WriteViewState, WriteEvent, WriteSideEffect, WriteViewModel>(
        FragmentWriteBinding::inflate,
    ) {
    override val viewModel: WriteViewModel by viewModels()

    override fun initView() {
    }
}
