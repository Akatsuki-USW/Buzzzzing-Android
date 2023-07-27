package com.onewx2m.recommend_place.ui.write

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.util.ImageUtil
import com.onewx2m.core_ui.util.ResourceProvider
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortItem
import com.onewx2m.design_system.components.recyclerview.kakaolocation.KakaoLocationItem
import com.onewx2m.design_system.components.recyclerview.picture.PictureItem
import com.onewx2m.design_system.components.recyclerview.spotcategoryselector.SpotCategoryItem
import com.onewx2m.domain.usecase.GetSpotCategoryUseCase
import com.onewx2m.domain.usecase.PostSpotUseCase
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val getSpotCategoryUseCase: GetSpotCategoryUseCase,
    private val postSpotUseCase: PostSpotUseCase,
    private val resourceProvider: ResourceProvider,
    private val imageUtil: ImageUtil,
) :
    MviViewModel<WriteViewState, WriteEvent, WriteSideEffect>(
        WriteViewState(),
    ) {
    private var locationId: Int? = null

    // TODO 스팟 수정할 때 사용할듯
    private val toDeleteUrls: MutableList<String> = mutableListOf()

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
                needTitleRender = event.needRender,
            )

            is WriteEvent.UpdateContent -> current.copy(
                content = event.content,
                needContentRender = event.needRender,
            )

            is WriteEvent.UpdateSelectedSpotCategoryItem -> current.copy(
                selectedSpotCategoryItem = event.selectedSpotCategoryItem,
            )

            is WriteEvent.UpdateSpotCategoryItems -> current.copy(
                spotCategoryItems = event.spotCategoryItems,
            )

            is WriteEvent.UpdatePictures -> current.copy(
                pictures = event.pictures,
            )
        }
    }

    fun initSpotCategoryItems() = viewModelScope.launch(Dispatchers.IO) {
        val categoryItems = getSpotCategoryUseCase().first()
            .map { SpotCategoryItem(id = it.id, name = it.name) }

        postEvent(WriteEvent.UpdateSpotCategoryItems(categoryItems))
    }

    fun updateSelectedCategoryItem(item: SpotCategoryItem) {
        postEvent(WriteEvent.UpdateSelectedSpotCategoryItem(if (item == state.value.selectedSpotCategoryItem) null else item))
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

    fun updateTitle(title: String, needRender: Boolean = false) {
        postEvent(
            WriteEvent.UpdateTitle(title, needRender),
        )
    }

    fun updateContent(content: String, needRender: Boolean = false) {
        postEvent(
            WriteEvent.UpdateContent(content, needRender),
        )
    }

    fun updatePictures(uris: List<Uri>) {
        val uriQueue = ArrayDeque<Uri>()
        uriQueue.addAll(uris)

        val pictures = state.value.pictures.map {
            if (it.url.isBlank()) {
                PictureItem(uri = uriQueue.removeFirstOrNull())
            } else {
                it
            }
        }.toMutableList()

        while (uriQueue.isNotEmpty()) {
            pictures.add(PictureItem(uri = uriQueue.removeFirstOrNull()))
        }

        postEvent(
            WriteEvent.UpdatePictures(
                pictures,
            ),
        )
    }

    fun removePicture(item: PictureItem) {
        if (item.url.isNotBlank()) toDeleteUrls.add(item.url)
        val pictures = state.value.pictures.minus(item)
        postEvent(WriteEvent.UpdatePictures(pictures))
    }

    fun showImagePicker() {
        postSideEffect(WriteSideEffect.GetPermissionAndShowImagePicker)
    }

    fun postSpot() = viewModelScope.launch {
        val files = state.value.pictureUris.map { uri ->
            imageUtil.uriToOptimizeImageFile(uri) ?: return@launch
        }

        with(state.value) {
            val spotCategoryId = selectedSpotCategoryItem?.id ?: return@launch
            val locationId = locationId ?: return@launch
            postSpotUseCase(
                spotCategoryId = spotCategoryId,
                locationId = locationId,
                title = title,
                address = kakaoLocation,
                content = content,
                files,
            ).collect()
        }
    }
}
