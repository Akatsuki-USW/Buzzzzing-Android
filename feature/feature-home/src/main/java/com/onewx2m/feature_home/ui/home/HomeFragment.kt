package com.onewx2m.feature_home.ui.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumAdapter
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.design_system.components.recyclerview.buzzzzingsmall.BuzzzzingSmallItem
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_home.databinding.FragmentHomeBinding
import com.onewx2m.feature_home.ui.home.adapter.HomeBuzzzzingMediumAdapter
import com.onewx2m.feature_home.ui.home.adapter.HomeBuzzzzingSmallAdapter
import com.onewx2m.feature_home.ui.home.adapter.HomeHeaderAdapter
import com.onewx2m.feature_home.ui.home.adapter.HomeSearchAdapter
import com.onewx2m.mvi.MviFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment :
    MviFragment<FragmentHomeBinding, HomeViewState, HomeEvent, HomeSideEffect, HomeViewModel>(
        FragmentHomeBinding::inflate,
    ) {
    override val viewModel: HomeViewModel by viewModels()

    private val homeHeaderAdapter: HomeHeaderAdapter by lazy {
        HomeHeaderAdapter()
    }

    private val homeBuzzzzingSmallAdapter: HomeBuzzzzingSmallAdapter by lazy {
        HomeBuzzzzingSmallAdapter(
            onBookmarkClick = {
                ErrorToast.make(binding.root, "$it 북마크 클릭").show()
            },
            onItemClick = {
                ErrorToast.make(binding.root, "$it 아이템 클릭").show()
            },
        )
    }

    private val homeSearchAdapter: HomeSearchAdapter by lazy {
        HomeSearchAdapter()
    }

    private val buzzzzingMediumAdapter: BuzzzzingMediumAdapter by lazy {
        BuzzzzingMediumAdapter(
            onBookmarkClick = {
                ErrorToast.make(binding.root, "$it 북마크 클릭").show()
            },
            onItemClick = {
                ErrorToast.make(binding.root, "$it 아이템 클릭").show()
            },
        )
    }

    var list = listOf(
        BuzzzzingMediumItem(
            1,
            false,
            "지하철",
            "RELAX",
            "https://avatars.githubusercontent.com/u/81678959?v=4",
        ),
        BuzzzzingMediumItem(
            2,
            false,
            "지하철",
            "NORMAL",
            "https://avatars.githubusercontent.com/u/81678959?v=4",
        ),
        BuzzzzingMediumItem(3, true, "놀이공원", "RELAX", "RELAX"),
        BuzzzzingMediumItem(
            4,
            false,
            "도서관",
            "CONGESTION",
            "https://avatars.githubusercontent.com/u/81678959?v=4",
        ),
        BuzzzzingMediumItem(5, false, "지하철", "RELAX", "RELAX"),
    )

    var lastKey = 5
    fun load(): List<BuzzzzingMediumItem> {
        lastKey += 5
        val temp = mutableListOf<BuzzzzingMediumItem>()

        repeat(5) {
            temp.add(
                BuzzzzingMediumItem(
                    lastKey + it + 1,
                    false,
                    "테스트 ${lastKey + it + 1}",
                    "CONGESTION",
                    "",
                ),
            )
        }

        return temp.toList()
    }

    override fun initView() {
        val concatAdapter = ConcatAdapter(
            homeHeaderAdapter,
            homeBuzzzzingSmallAdapter,
            homeSearchAdapter,
            buzzzzingMediumAdapter,
        )

        binding.recyclerView.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
            infiniteScrolls {
                val add = load()
                buzzzzingMediumAdapter.submitList(
                    list + add,
                )
                list = list.plus(add)
            }
        }

        MainScope().launch {
            delay(200L)
            homeBuzzzzingSmallAdapter.submitList(
                listOf(
                    BuzzzzingSmallItem(1, false, "지하철", "테스트1", "RELAX"),
                    BuzzzzingSmallItem(2, false, "지하철", "테스트2", "NORMAL"),
                    BuzzzzingSmallItem(3, true, "놀이공원", "테스트3", "RELAX"),
                    BuzzzzingSmallItem(4, false, "도서관", "테스트4", "CONGESTION"),
                    BuzzzzingSmallItem(5, false, "지하철", "테스트5", "RELAX"),
                ),
            )

            buzzzzingMediumAdapter.submitList(
                list.toList(),
            )
        }
    }
}
