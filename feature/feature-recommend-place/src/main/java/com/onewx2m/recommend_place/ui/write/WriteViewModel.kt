package com.onewx2m.recommend_place.ui.write

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor() :
    MviViewModel<WriteViewState, WriteEvent, WriteSideEffect>(
        WriteViewState(),
    ) {
    override fun reduceState(current: WriteViewState, event: WriteEvent): WriteViewState {
        return current
    }

    fun doWhenKeyboardShow(currentScrollY: Int, additionalScroll: Int) {
        if (currentScrollY < additionalScroll) {
            postSideEffect(
                WriteSideEffect.MoreScroll(
                    additionalScroll - currentScrollY,
                ),
            )
        }
    }
}
