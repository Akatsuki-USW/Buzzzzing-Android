package com.onewx2m.recommend_place.ui.spotdetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.hideKeyboard
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.core_ui.extensions.setGoneWithAnimation
import com.onewx2m.core_ui.extensions.setVisibleWithAnimation
import com.onewx2m.core_ui.extensions.showKeyboard
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.dialog.CommonDialog
import com.onewx2m.design_system.components.powermenu.PowerMenuType
import com.onewx2m.design_system.components.powermenu.showPowerMenu
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentAdapter
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentItem
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.mvi.MviFragment
import com.onewx2m.recommend_place.R
import com.onewx2m.recommend_place.databinding.FragmentSpotDetailBinding
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentAdapter
import com.onewx2m.recommend_place.ui.spotdetail.bottomsheet.EditCommentBottomSheet
import com.onewx2m.recommend_place.ui.write.bottomsheet.KakaoLocationBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpotDetailFragment :
    MviFragment<FragmentSpotDetailBinding, SpotDetailViewState, SpotDetailEvent, SpotDetailSideEffect, SpotDetailViewModel>(
        FragmentSpotDetailBinding::inflate,
    ) {
    override val viewModel: SpotDetailViewModel by viewModels()
    private val navArgs by navArgs<SpotDetailFragmentArgs>()

    private val spotDetailContentAdapter: SpotDetailContentAdapter by lazy {
        SpotDetailContentAdapter(
            onBookmarkClick = viewModel::bookmark,
        ).apply {
            setHasStableIds(true)
        }
    }

    private val spotParentCommentAdapter: SpotParentCommentAdapter by lazy {
        SpotParentCommentAdapter(
            onParentMeatBallClick = viewModel::showParentCommentPowerMenu,
            onChildMeatBallClick = viewModel::showChildrenCommentPowerMenu,
            onMoreClick = { item -> viewModel.getChildrenComments(item.id) },
        ).apply {
            setHasStableIds(true)
        }
    }

    private val commonDialog by lazy {
        CommonDialog(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initData(navArgs.spotId)
    }

    override fun initView() {
        val concatAdapter = ConcatAdapter(
            spotDetailContentAdapter,
            spotParentCommentAdapter,
        )

        binding.recyclerView.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            infiniteScrolls {
                viewModel.getSpotParentCommentList(navArgs.spotId)
            }
        }

        binding.imageViewArticleMeatBall.onThrottleClick {
            viewModel.showContentPowerMenu()
        }

        binding.imageViewBack.onThrottleClick {
            viewModel.goToPrevPage()
        }

        binding.editTextComment.doOnTextChanged { text, _, _, _ ->
            viewModel.updateContent(text.toString())
        }

        binding.imageViewReplyClose.onThrottleClick {
            viewModel.onReplyCloseClick()
        }

        binding.imageViewSend.onThrottleClick {
            viewModel.onClickCommentSend(navArgs.spotId)
        }
    }

    override fun render(current: SpotDetailViewState) {
        super.render(current)

        with(binding) {
            if (lottieLoading.isVisible && current.isLoadingLottieVisible.not()) {
                recyclerView.setVisibleWithAnimation()
                lottieLoading.setGoneWithAnimation()
            }

            lottieLoadingSmall.visibility =
                if (current.isSmallLottieVisible) View.VISIBLE else View.GONE

            constraintLayoutReply.visibility =
                if (current.isReplyLayoutVisible) View.VISIBLE else View.GONE

            if (current.needCommentRender) {
                binding.editTextComment.setText(current.commentContent)
                binding.editTextComment.setSelection(current.commentContent.length)
            }

            textViewReply.text =
                getString(R.string.fragment_spot_detail_reply_to, current.replyNickname)
        }

        spotDetailContentAdapter.setData(
            current.spotDetailContent,
        )

        spotParentCommentAdapter.submitList(current.spotCommentList)
    }

    override fun handleSideEffect(sideEffect: SpotDetailSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            SpotDetailSideEffect.GoToLoginFragment -> goToLoginFragment()
            SpotDetailSideEffect.GoToWriteFragment -> goToWriteFragment()
            is SpotDetailSideEffect.ShowContentPowerMenu -> showContentPowerMenu()
            is SpotDetailSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg)
                .show()

            SpotDetailSideEffect.GoToPrevPage -> findNavController().popBackStack()
            is SpotDetailSideEffect.ShowChildCommentPowerMenu -> showChildCommentPowerMenu(
                sideEffect.view,
                sideEffect.item,
            )

            is SpotDetailSideEffect.ShowParentCommentPowerMenu -> showParentCommentPowerMenu(
                sideEffect.view,
                sideEffect.item,
            )

            SpotDetailSideEffect.ShowKeyboard -> binding.editTextComment.showKeyboard()
            SpotDetailSideEffect.HideKeyboard -> hideKeyboard()
            is SpotDetailSideEffect.ShowBlockDialog -> showBlockDialog(sideEffect.userId)
            is SpotDetailSideEffect.ShowCommentDeleteDialog -> showCommentDelete(sideEffect.commentId)
            SpotDetailSideEffect.ShowSpotDeleteDialog -> showSpotDelete()
            is SpotDetailSideEffect.ShowEditCommentBottomSheet -> showEditCommentBottomSheet(sideEffect.content, sideEffect.commentId)
            SpotDetailSideEffect.GoToHomeFragment -> goToHomeFragment()
        }
    }

    private fun goToHomeFragment() {
        val (request, navOptions) = DeepLinkUtil.getHomeRequestAndOption(
            requireContext(),
            findNavController().graph.id,
            true,
        )
        findNavController().navigate(request, navOptions)
    }

    private fun showBlockDialog(userId: Int) {
        commonDialog.setDescription(com.onewx2m.design_system.R.string.dialog_block_description)
            .setTitle(com.onewx2m.design_system.R.string.dialog_block_title)
            .setNegativeButton {
                commonDialog.dismiss()
            }
            .setPositiveButton {
                viewModel.blockUser(userId)
                commonDialog.dismiss()
            }.show()
    }

    private fun showSpotDelete() {
        commonDialog.setTitle(com.onewx2m.design_system.R.string.dialog_delete_title)
            .setDescription(com.onewx2m.design_system.R.string.dialog_delete_description)
            .setNegativeButton {
                commonDialog.dismiss()
            }
            .setPositiveButton {
                // TODO 스팟 삭제 navArgs.spotId
                commonDialog.dismiss()
            }.show()
    }

    private fun showCommentDelete(commentId: Int) {
        commonDialog.setDescription(com.onewx2m.design_system.R.string.dialog_delete_description)
            .setTitle(com.onewx2m.design_system.R.string.dialog_delete_title)
            .setNegativeButton {
                commonDialog.dismiss()
            }
            .setPositiveButton {
                viewModel.deleteComment(commentId)
                commonDialog.dismiss()
            }.show()
    }

    private fun goToLoginFragment() {
        val (request, navOptions) = DeepLinkUtil.getLoginRequestAndOption(
            requireContext(),
            findNavController().graph.id,
            true,
        )
        findNavController().navigate(request, navOptions)
    }

    private fun goToWriteFragment() {
        // TODO WriteContent 넣어야함.
        val (request, navOptions) = DeepLinkUtil.getWriteRequestAndOption(requireContext())
        findNavController().navigate(request, navOptions)
    }

    private fun showContentPowerMenu() {
        binding.imageViewArticleMeatBall.showPowerMenu(
            0,
            0,
            viewLifecycleOwner,
            viewModel.contentPowerMenuList,
        ) { _, item ->
            when (PowerMenuType.typeOf(item)) {
                PowerMenuType.EDIT -> viewModel.goToWriteFragment()
                PowerMenuType.DELETE -> viewModel.showDeleteSpotDialog()
                PowerMenuType.REPORT -> Unit
                PowerMenuType.BLOCK -> viewModel.showBlockDialog(viewModel.authorId)
                PowerMenuType.REPLY -> Unit
            }
        }
    }

    private fun showParentCommentPowerMenu(view: View, item: SpotParentCommentItem) {
        view.showPowerMenu(
            0,
            0,
            viewLifecycleOwner,
            viewModel.getParentCommentPowerMenuList(item.isAuthor),
        ) { _, menu ->
            when (PowerMenuType.typeOf(menu)) {
                PowerMenuType.EDIT -> viewModel.showCommentBottomSheet(item.content, item.id)
                PowerMenuType.DELETE -> viewModel.showDeleteCommentDialog(item.id)
                PowerMenuType.REPORT -> Unit
                PowerMenuType.BLOCK -> viewModel.showBlockDialog(item.userId)
                PowerMenuType.REPLY -> viewModel.onClickReply(item.id, item.nickname)
            }
        }
    }

    private fun showChildCommentPowerMenu(view: View, item: SpotChildrenCommentItem) {
        view.showPowerMenu(
            0,
            0,
            viewLifecycleOwner,
            viewModel.getChildrenCommentPowerMenuList(item.isAuthor),
        ) { _, menu ->
            when (PowerMenuType.typeOf(menu)) {
                PowerMenuType.EDIT -> viewModel.showCommentBottomSheet(item.content, item.id)
                PowerMenuType.DELETE -> viewModel.showDeleteCommentDialog(item.id)
                PowerMenuType.REPORT -> Unit
                PowerMenuType.BLOCK -> viewModel.showBlockDialog(item.userId)
                PowerMenuType.REPLY -> Unit
            }
        }
    }

    private fun showEditCommentBottomSheet(content: String, commentId: Int) {
        EditCommentBottomSheet.newInstance(content) {
            viewModel.editComment(commentId, it)
        }.show(parentFragmentManager, "")
    }
}
