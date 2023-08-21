package com.onewx2m.recommend_place.ui.write.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortItem
import com.onewx2m.design_system.components.recyclerview.kakaolocation.KakaoLocationItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.KakaoLocationInfo
import com.onewx2m.domain.usecase.GetKakaoLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class KakaoLocationBottomSheetViewModel @Inject constructor(
    private val getKakaoLocationUseCase: GetKakaoLocationUseCase,
) : ViewModel() {
    private var page = 1
    private var query = ""
    private var last = false

    private val kakaoLocationItems = mutableListOf<KakaoLocationItem>()

    suspend fun getKakaoLocation(query: String = "", needClear: Boolean = false) =
        viewModelScope.async<List<KakaoLocationItem>> {
            if (needClear) {
                last = false
                page = 1
                kakaoLocationItems.clear()
                this@KakaoLocationBottomSheetViewModel.query = query
            }

            if (last) return@async kakaoLocationItems
            val response = getKakaoLocationUseCase(
                this@KakaoLocationBottomSheetViewModel.query,
                page,
            ).firstOrNull()
            processKakaoLocationItems(response)
            kakaoLocationItems.toList()
        }.await()

    private fun processKakaoLocationItems(response: Outcome<KakaoLocationInfo>?) {
        response?.let {
            if (it is Outcome.Success<KakaoLocationInfo>) {
                kakaoLocationItems.remove(KakaoLocationItem(ItemViewType.LOADING))
                kakaoLocationItems.remove(KakaoLocationItem(ItemViewType.EMPTY))

                kakaoLocationItems.addAll(
                    it.data.documents.map { document ->
                        KakaoLocationItem(
                            placeName = document.placeName,
                            addressName = document.addressName,
                            roadAddressName = document.roadAddressName,
                        )
                    },
                )

                if(page == 1 && it.data.meta.isEnd && it.data.documents.isEmpty()) {
                    kakaoLocationItems.clear()
                    kakaoLocationItems.add(KakaoLocationItem(ItemViewType.EMPTY))
                }

                if (it.data.meta.isEnd.not()) kakaoLocationItems.add(KakaoLocationItem(ItemViewType.LOADING))

                last = it.data.meta.isEnd
                page += 1
            }
        }
    }
}
