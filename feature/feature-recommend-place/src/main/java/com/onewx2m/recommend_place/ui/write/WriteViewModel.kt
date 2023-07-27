package com.onewx2m.recommend_place.ui.write

import com.onewx2m.core_ui.util.ResourceProvider
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortItem
import com.onewx2m.design_system.components.recyclerview.kakaolocation.KakaoLocationItem
import com.onewx2m.mvi.MviViewModel
import com.onewx2m.recommend_place.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
) :
    MviViewModel<WriteViewState, WriteEvent, WriteSideEffect>(
        WriteViewState(),
    ) {
    private var locationId: Int? = null

    override fun reduceState(current: WriteViewState, event: WriteEvent): WriteViewState {
        return when (event) {
            is WriteEvent.ChangeBuzzzzingLocationInputLayout -> current.copy(
                buzzzzingLocation = event.buzzzzingLocation,
            )

            is WriteEvent.ChangeKakaoLocationInputLayout -> current.copy(
                kakaoLocation = event.kakaoLocation,
            )
        }
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

    fun postShowBuzzzzingLocationBottomSheetSideEffect() {
        postSideEffect(WriteSideEffect.ShowBuzzzzingLocationBottomSheet)
    }

    fun postShowKakaoLocationBottomSheetSideEffect() {
        postSideEffect(WriteSideEffect.ShowKakaoLocationBottomSheet)
    }

    fun onClickKakaoLocationItem(item: KakaoLocationItem) {
        postEvent(
            WriteEvent.ChangeKakaoLocationInputLayout(
                resourceProvider.getString(
                    com.onewx2m.core_ui.R.string.word_comma,
                    item.placeName,
                    item.roadAddressName.ifBlank { item.addressName },
                ),
            ),
        )
    }

    fun onClickBuzzzzingLocationItem(item: BuzzzzingShortItem) {
        locationId = item.id
        postEvent(
            WriteEvent.ChangeBuzzzzingLocationInputLayout(
                item.name,
            ),
        )
    }
}
