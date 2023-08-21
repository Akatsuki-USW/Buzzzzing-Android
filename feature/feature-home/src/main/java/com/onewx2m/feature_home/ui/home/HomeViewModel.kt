package com.onewx2m.feature_home.ui.home

import androidx.lifecycle.viewModelScope
import com.onewx2m.core_ui.util.ResourceProvider
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.design_system.components.recyclerview.buzzzzingsmall.BuzzzzingSmallItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.collectOutcome
import com.onewx2m.domain.exception.common.CommonException
import com.onewx2m.domain.model.BuzzzzingContent
import com.onewx2m.domain.model.BuzzzzingLocation
import com.onewx2m.domain.model.BuzzzzingLocationBookmark
import com.onewx2m.domain.model.category.CongestionLevelCategory
import com.onewx2m.domain.model.category.LocationCategory
import com.onewx2m.domain.usecase.BookmarkBuzzzzingLocationUseCase
import com.onewx2m.domain.usecase.GetBuzzzzingLocationTop5UseCase
import com.onewx2m.domain.usecase.GetBuzzzzingLocationUseCase
import com.onewx2m.domain.usecase.GetCongestionLevelCategoryUseCase
import com.onewx2m.domain.usecase.GetLocationCategoryUseCase
import com.onewx2m.feature_home.R
import com.onewx2m.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCongestionLevelCategoryUseCase: GetCongestionLevelCategoryUseCase,
    private val getLocationCategoryUseCase: GetLocationCategoryUseCase,
    private val getBuzzzzingLocationUseCase: GetBuzzzzingLocationUseCase,
    private val getBuzzzzingLocationTop5UseCase: GetBuzzzzingLocationTop5UseCase,
    private val bookmarkBuzzzzingLocationUseCase: BookmarkBuzzzzingLocationUseCase,
    private val resourceProvider: ResourceProvider,
) : MviViewModel<HomeViewState, HomeEvent, HomeSideEffect>(
    HomeViewState(),
) {
    private lateinit var congestionLevelCategory: List<CongestionLevelCategory>
    private lateinit var locationCategory: List<LocationCategory>

    lateinit var congestionLevelCategoryValues: List<String>
    lateinit var locationCategoryValues: List<String>

    private var cursorId: Int = 0
    private var keyword: String? = null
    private var categoryId: Int? = null
    private var congestionSort: String = ""
    private var cursorCongestionLevel: Int? = null
    private var last: Boolean = false

    fun initData() = viewModelScope.launch {
        initCategory().join()
        joinAll(getBuzzzzingLocationTop5(), getBuzzzzingLocation())
        postEvent(HomeEvent.Initialized)
    }

    private fun initCategory() = viewModelScope.launch(IO) {
        getCongestionLevelCategoryUseCase().zip(getLocationCategoryUseCase()) { level, location ->
            congestionLevelCategory = level
            locationCategory = location
        }.first()

        congestionLevelCategoryValues = congestionLevelCategory.map { it.value }
        locationCategoryValues =
            listOf(resourceProvider.getString(com.onewx2m.core_ui.R.string.word_all_place)) + locationCategory.map { it.name }

        clearFilter()
    }

    private fun clearFilter() {
        congestionSort = congestionLevelCategory[0].key

        updateLocationFilter(locationCategoryValues[0])
        updateCongestionFilter(congestionLevelCategory[0].value)
    }

    fun onClickLocationFilterItemClick(locationFilter: String) {
        updateLocationFilter(locationFilter)
        categoryId = locationCategory.find { it.name == locationFilter }?.id
        getBuzzzzingLocation(true)
    }

    private fun updateLocationFilter(locationFilter: String) {
        postEvent(HomeEvent.UpdateLocationFilter(locationFilter = locationFilter))
    }

    fun onClickCongestionFilterItemClick(congestionFilter: String) {
        updateCongestionFilter(congestionFilter)
        congestionSort = congestionLevelCategory.find { it.value == congestionFilter }?.key
            ?: congestionLevelCategory[0].key
        getBuzzzzingLocation(true)
    }

    private fun updateCongestionFilter(congestionFilter: String) {
        postEvent(HomeEvent.UpdateCongestionFilter(congestionFilter = congestionFilter))
    }

    fun postShowLocationBottomSheetSideEffect() {
        postSideEffect(HomeSideEffect.ShowLocationBottomSheet)
    }

    fun postShowCongestionBottomSheetSideEffect() {
        postSideEffect(HomeSideEffect.ShowCongestionBottomSheet)
    }

    fun getBuzzzzingLocation(needClear: Boolean = false) = viewModelScope.launch {
        if (needClear) {
            clearCursorAndList()
        }

        if (last) return@launch

        getBuzzzzingLocationUseCase(
            cursorId = cursorId,
            keyword = keyword,
            categoryId = categoryId,
            congestionSort = congestionSort,
            cursorCongestionLevel = cursorCongestionLevel,
        ).collectOutcome(
            handleSuccess = ::handleGetLocationSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetLocationSuccess(outcome: Outcome.Success<BuzzzzingLocation>) {
        updateCursor(outcome)

        postEvent(
            HomeEvent.UpdateBuzzzzingMediumItem(
                getProcessedItem(outcome.data.content),
            ),
        )
    }

    private fun getProcessedItem(content: List<BuzzzzingContent>): List<BuzzzzingMediumItem> {
        if (cursorId == 0 && last) {
            return listOf(
                BuzzzzingMediumItem(type = ItemViewType.EMPTY),
            )
        }

        val itemMapped = content.map { data ->
            BuzzzzingMediumItem(
                id = data.id,
                isBookmarked = data.isBookmarked,
                locationName = data.name,
                congestionSymbol = data.congestionSymbol,
                iconUrl = data.categoryIconUrl,
            )
        }

        val lastItem = if (last) {
            emptyList()
        } else {
            listOf(
                BuzzzzingMediumItem(type = ItemViewType.LOADING),
            )
        }

        return state.value.buzzzzingMediumItem
            .minus(BuzzzzingMediumItem(type = ItemViewType.LOADING))
            .minus(BuzzzzingMediumItem(type = ItemViewType.EMPTY))
            .plus(itemMapped)
            .plus(lastItem)
    }

    private fun updateCursor(outcome: Outcome.Success<BuzzzzingLocation>) {
        last = outcome.data.last
        outcome.data.content.lastOrNull()?.also { lastItem ->
            cursorId = lastItem.id
            cursorCongestionLevel = lastItem.congestionLevel
        }
    }

    private fun clearCursorAndList() {
        cursorId = 0
        cursorCongestionLevel = null
        last = false
        postEvent(HomeEvent.UpdateBuzzzzingMediumItem(emptyList()))
    }

    private fun getBuzzzzingLocationTop5() = viewModelScope.launch {
        getBuzzzzingLocationTop5UseCase().collectOutcome(
            handleSuccess = ::handleGetBuzzzzingLocationTop5Success,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleGetBuzzzzingLocationTop5Success(outcome: Outcome.Success<BuzzzzingLocation>) {
        postEvent(
            HomeEvent.UpdateBuzzzzingSmallItem(
                outcome.data.content.map {
                    BuzzzzingSmallItem(
                        id = it.id,
                        isBookmarked = it.isBookmarked,
                        locationCategory = it.categoryName,
                        locationName = it.name,
                        congestionSymbol = it.congestionSymbol,
                    )
                },
            ),
        )
    }

    fun onSearch(keyword: String) {
        this.keyword = keyword
        postEvent(HomeEvent.UpdateKeyword(keyword))
        getBuzzzzingLocation(true)
    }

    fun onSearchClear() {
        clearFilter()
        onSearch("")
    }

    fun bookmark(locationId: Int) = viewModelScope.launch {
        bookmarkBuzzzzingLocationUseCase(locationId).collectOutcome(
            handleSuccess = ::handleBookmarkSuccess,
            handleFail = { handleError(it.error) },
        )
    }

    private fun handleBookmarkSuccess(outcome: Outcome.Success<BuzzzzingLocationBookmark>) {
        handleBookmarkSmallSuccess(outcome)
        handleBookmarkMediumSuccess(outcome)
    }

    private fun handleBookmarkSmallSuccess(
        outcome: Outcome.Success<BuzzzzingLocationBookmark>,
    ) {
        val bookmarkUpdatedItem = state.value.buzzzzingSmallItem.map {
            if (it.id == outcome.data.locationId) {
                it.copy(isBookmarked = outcome.data.isBookmarked)
            } else {
                it
            }
        }

        postEvent(HomeEvent.UpdateBuzzzzingSmallItem(bookmarkUpdatedItem))
    }

    private fun handleBookmarkMediumSuccess(
        outcome: Outcome.Success<BuzzzzingLocationBookmark>,
    ) {
        val bookmarkUpdatedItem = state.value.buzzzzingMediumItem.map {
            if (it.id == outcome.data.locationId) {
                it.copy(isBookmarked = outcome.data.isBookmarked)
            } else {
                it
            }
        }

        postEvent(HomeEvent.UpdateBuzzzzingMediumItem(bookmarkUpdatedItem))
    }

    private fun handleError(error: Throwable?) {
        when (error) {
            is CommonException.NeedLoginException -> {
                postSideEffect(HomeSideEffect.ShowErrorToast(error.snackBarMessage))
                postSideEffect(HomeSideEffect.GoToLoginFragment)
            }

            else -> postSideEffect(
                HomeSideEffect.ShowErrorToast(
                    error?.message ?: CommonException.UnknownException().snackBarMessage,
                ),
            )
        }
    }

    fun goToLocationDetailFragment(locationId: Int) {
        postSideEffect(HomeSideEffect.GoToLocationDetailFragment(locationId))
    }

    override fun reduceState(current: HomeViewState, event: HomeEvent): HomeViewState =
        when (event) {
            is HomeEvent.UpdateBuzzzzingMediumItem -> current.copy(buzzzzingMediumItem = event.buzzzzingMediumItem)
            is HomeEvent.UpdateCongestionFilter -> current.copy(congestionFilter = event.congestionFilter)
            is HomeEvent.UpdateLocationFilter -> current.copy(locationFilter = event.locationFilter)
            is HomeEvent.UpdateKeyword -> current.copy(keyword = event.keyword)
            is HomeEvent.UpdateBuzzzzingSmallItem -> current.copy(buzzzzingSmallItem = event.buzzzzingSmallItem)
            HomeEvent.Initialized -> current.copy(isInitializing = false)
        }
}
