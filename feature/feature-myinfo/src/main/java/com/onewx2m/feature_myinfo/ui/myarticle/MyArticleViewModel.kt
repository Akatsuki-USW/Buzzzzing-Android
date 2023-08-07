package com.onewx2m.feature_myinfo.ui.myarticle

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyArticleViewModel @Inject constructor() : MviViewModel<
    MyArticleViewState, MyArticleEvent, MyArticleSideEffect,
    >(MyArticleViewState()) {
    var viewPagerPosition = 0

    override fun reduceState(
        current: MyArticleViewState,
        event: MyArticleEvent,
    ): MyArticleViewState {
        return current
    }
}
