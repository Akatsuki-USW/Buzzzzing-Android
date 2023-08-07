package com.onewx2m.feature_myinfo.ui.myarticle

import androidx.fragment.app.viewModels
import com.onewx2m.feature_myinfo.databinding.FragmentMyArticleBinding
import com.onewx2m.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyArticleFragment :
    MviFragment<FragmentMyArticleBinding, MyArticleViewState, MyArticleEvent, MyArticleSideEffect, MyArticleViewModel>(
        FragmentMyArticleBinding::inflate,
    ) {
    override val viewModel: MyArticleViewModel by viewModels()

    override fun initView() {
    }
}
