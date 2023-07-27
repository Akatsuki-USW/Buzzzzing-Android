package com.onewx2m.recommend_place.ui.write.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onewx2m.design_system.components.recyclerview.buzzzzingshort.BuzzzzingShortItem
import com.onewx2m.design_system.components.recyclerview.kakaolocation.KakaoLocationItem
import com.onewx2m.design_system.enum.Congestion
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.model.BuzzzzingLocation
import com.onewx2m.domain.usecase.GetBuzzzzingLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class BuzzzzingLocationBottomSheetViewModel @Inject constructor(
    private val getBuzzzzingLocationUseCase: GetBuzzzzingLocationUseCase,
) : ViewModel() {
    private var cursorId = 0
    private var keyword: String? = null
    private var last = false
    private var cursorCongestionLevel: Int? = null

    private val buzzzzingShortItems = mutableListOf<BuzzzzingShortItem>()

    suspend fun getBuzzzzingLocation(keyword: String = "", needClear: Boolean = false) =
        viewModelScope.async<List<BuzzzzingShortItem>> {
            if (needClear) {
                last = false
                cursorId = 0
                cursorCongestionLevel = null
                buzzzzingShortItems.clear()
                this@BuzzzzingLocationBottomSheetViewModel.keyword = keyword
            }

            if (last) return@async buzzzzingShortItems
            val response = getBuzzzzingLocationUseCase(
                cursorId = cursorId,
                keyword = keyword,
                categoryId = null,
                congestionSort = Congestion.NORMAL.name,
                cursorCongestionLevel = cursorCongestionLevel,
            ).firstOrNull()
            processBuzzzzingLocationItems(response)
            buzzzzingShortItems.toList()
        }.await()

    private fun processBuzzzzingLocationItems(response: Outcome<BuzzzzingLocation>?) {
        response?.let {
            if (it is Outcome.Success<BuzzzzingLocation>) {
                buzzzzingShortItems.remove(BuzzzzingShortItem(ItemViewType.LOADING))

                buzzzzingShortItems.addAll(
                    it.data.content.map { document ->
                        BuzzzzingShortItem(
                            id = document.id,
                            name = document.name,
                            categoryIconUrl = document.categoryIconUrl,
                        )
                    },
                )

                last = it.data.last
                val lastItem = it.data.content.last()
                cursorId = lastItem.id
                cursorCongestionLevel = lastItem.congestionLevel

                if (it.data.last.not()) buzzzzingShortItems.add(BuzzzzingShortItem(ItemViewType.LOADING))
            }
        }
    }
}
