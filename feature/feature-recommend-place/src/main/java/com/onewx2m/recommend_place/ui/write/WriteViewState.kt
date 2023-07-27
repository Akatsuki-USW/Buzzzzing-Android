package com.onewx2m.recommend_place.ui.write

import com.onewx2m.design_system.components.textinputlayout.TextInputLayoutState
import com.onewx2m.mvi.ViewState

data class WriteViewState(
    val kakaoLocation: String = "",
    val buzzzzingLocation: String = "",
) : ViewState
