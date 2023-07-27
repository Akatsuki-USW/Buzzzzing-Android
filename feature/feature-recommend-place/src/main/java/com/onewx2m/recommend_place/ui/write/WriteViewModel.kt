package com.onewx2m.recommend_place.ui.write

import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.util.ResourceProvider
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortItem
import com.onewx2m.design_system.components.recyclerview.kakaolocation.KakaoLocationItem
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.domain.usecase.GetSpotCategoryUseCase
import com.onewx2m.mvi.MviViewModel
import com.onewx2m.recommend_place.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val getSpotCategoryUseCase: GetSpotCategoryUseCase,
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

            is WriteEvent.UpdateTitle -> current.copy(
                title = event.title,
            )

            is WriteEvent.UpdateContent -> current.copy(
                content = event.content,
            )

            is WriteEvent.UpdateSelectedSpotCategoryItem -> current.copy(
                selectedSpotCategoryItem = event.selectedSpotCategoryItem,
            )

            is WriteEvent.UpdateSpotCategoryItems -> current.copy(
                spotCategoryItems = event.spotCategoryItems,
            )
        }
    }

    fun initSpotCategoryItems() = viewModelScope.launch(Dispatchers.IO) {
        val categoryItems = getSpotCategoryUseCase().first()
            .map { SpotCategoryItem(id = it.id, name = it.name) }

        postEvent(WriteEvent.UpdateSpotCategoryItems(categoryItems))
    }

    fun updateSelectedCategoryItem(item: SpotCategoryItem) {
        postEvent(WriteEvent.UpdateSelectedSpotCategoryItem(if(item == state.value.selectedSpotCategoryItem) null else item))
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

    fun updateTitle(title: String) {
        postEvent(
            WriteEvent.UpdateTitle(title),
        )
    }

    fun updateContent(content: String) {
        postEvent(
            WriteEvent.UpdateContent(content),
        )
    }
}
