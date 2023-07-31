package com.onewx2m.recommend_place.ui.spotdetail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.databinding.FragmentSpotDetailBinding
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentAdapter
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpotDetailFragment :
    MviFragment<FragmentSpotDetailBinding, SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect, SpotDetailViewModel>(
        FragmentSpotDetailBinding::inflate,
    ) {
    override val viewModel: SpotDetailViewModel by viewModels()

    private val spotDetailContentAdapter: SpotDetailContentAdapter by lazy {
        SpotDetailContentAdapter(
            onBookmarkClick = {
            },
        )
    }

    override fun initView() {
        val concatAdapter = ConcatAdapter(
            spotDetailContentAdapter,
        )

        binding.recyclerView.apply {
            adapter = concatAdapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            infiniteScrolls {
            }
        }
    }

    override fun render(current: SpotDetailViewState) {
        super.render(current)

        binding.recyclerView.visibility = View.VISIBLE
        binding.lottieLoading.visibility = View.GONE

        spotDetailContentAdapter.setData(
            SpotDetailContentItem(
                spotId = -1,
                profileImageUrl = "https://avatars.githubusercontent.com/u/81678959?v=4",
                nickname = "지누크",
                createdAt = "2020 20 02",
                isBookmarked = false,
                title = "테스트 제목",
                location = "테스트 로케이션",
                imageUrls = listOf(),
                content = "테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 테스트 ",
                commentCount = 15,
            )
        )
    }

    override fun handleSideEffect(sideEffect: SpotDetailSideEffect) {
        super.handleSideEffect(sideEffect)
    }
}
