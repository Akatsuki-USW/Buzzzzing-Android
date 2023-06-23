package com.onewx2m.buzzzzing.ui

import androidx.lifecycle.ViewModel
import com.onewx2m.domain.usecase.ReissueJwtUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val reissueJwtUseCase: ReissueJwtUseCase
): ViewModel() {

}