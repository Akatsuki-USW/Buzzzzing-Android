package com.onewx2m.feature_myinfo.ui.editmyinfo

import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditMyInfoViewModel @Inject constructor() :
    MviViewModel<EditMyInfoViewState, EditMyInfoEvent, EditMyInfoSideEffect>(
        EditMyInfoViewState(),
    ) {
    override fun reduceState(
        current: EditMyInfoViewState,
        event: EditMyInfoEvent,
    ): EditMyInfoViewState {
        TODO("Not yet implemented")
    }
}
