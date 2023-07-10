package com.onewx2m.feature_home.ui.home

import androidx.fragment.app.viewModels
import com.onewx2m.design_system.components.recyclerview.buzzzzingmedium.BuzzzzingMediumItem
import com.onewx2m.design_system.components.recyclerview.buzzzzingsmall.BuzzzzingSmallItem
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.feature_home.databinding.FragmentHomeBinding
import com.onewx2m.mvi.MviFragment

class HomeFragment :
    MviFragment<FragmentHomeBinding, HomeViewState, HomeEvent, HomeSideEffect, HomeViewModel>(
        FragmentHomeBinding::inflate,
    ) {
    override val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        binding.recyclerView.initAdapter(
            onBookmarkClick = {
                ErrorToast.make(binding.root, "$it 북마크 클릭").show()
            },
            onItemClick = {
                ErrorToast.make(binding.root, "$it 아이템 클릭").show()
            },
        )
        binding.recyclerView.submitList(
            listOf(
                BuzzzzingSmallItem(1, false, "지하철", "테스트1", "RELAX"),
                BuzzzzingSmallItem(2, false, "지하철", "테스트2", "NORMAL"),
                BuzzzzingSmallItem(3, true, "놀이공원", "테스트3", "RELAX"),
                BuzzzzingSmallItem(4, false, "도서관", "테스트4", "CONGESTION"),
                BuzzzzingSmallItem(5, false, "지하철", "테스트5", "RELAX"),
            ),
        )

        binding.recyclerViewMedium.initAdapter(
            onBookmarkClick = {
                ErrorToast.make(binding.root, "$it 북마크 클릭").show()
            },
            onItemClick = {
                ErrorToast.make(binding.root, "$it 아이템 클릭").show()
            },
        )
        binding.recyclerViewMedium.submitList(
            listOf(
                BuzzzzingMediumItem(1, false, "지하철", "RELAX", "https://avatars.githubusercontent.com/u/81678959?v=4"),
                BuzzzzingMediumItem(2, false, "지하철", "NORMAL", "https://avatars.githubusercontent.com/u/81678959?v=4"),
                BuzzzzingMediumItem(3, true, "놀이공원", "RELAX", "RELAX"),
                BuzzzzingMediumItem(4, false, "도서관", "CONGESTION", "https://avatars.githubusercontent.com/u/81678959?v=4"),
                BuzzzzingMediumItem(5, false, "지하철", "RELAX", "RELAX"),
            ),
        )
    }
}
