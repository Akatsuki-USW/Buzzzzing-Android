package com.onewx2m.recommend_place.ui.spotdetail

import android.view.View
import com.onewx2m.core_ui.model.WriteContent
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentItem
import com.onewx2m.mvi.SideEffect

sealed interface SpotDetailSideEffect : SideEffect {
    data class ShowErrorToast(val msg: String) : SpotDetailSideEffect
    object ShowReportSuccessToast : SpotDetailSideEffect
    object GoToLoginFragment : SpotDetailSideEffect
    data class GoToWriteFragment(val writeContent: WriteContent) : SpotDetailSideEffect
    object GoToHomeFragment : SpotDetailSideEffect
    object ShowContentPowerMenu : SpotDetailSideEffect
    data class ShowParentCommentPowerMenu(val view: View, val item: SpotParentCommentItem) :
        SpotDetailSideEffect

    data class ShowChildCommentPowerMenu(val view: View, val item: SpotChildrenCommentItem) :
        SpotDetailSideEffect

    object GoToPrevPage : SpotDetailSideEffect

    object ShowKeyboard : SpotDetailSideEffect
    object HideKeyboard : SpotDetailSideEffect

    data class ShowBlockDialog(val userId: Int) : SpotDetailSideEffect
    data class ShowCommentDeleteDialog(val commentId: Int) : SpotDetailSideEffect
    object ShowSpotDeleteDialog : SpotDetailSideEffect
    data class ShowEditCommentBottomSheet(val content: String, val commentId: Int) :
        SpotDetailSideEffect
    data class ShowSpotReportBottomSheet(val userId: Int, val reportId: Int) :
        SpotDetailSideEffect

    data class ShowCommentReportBottomSheet(val userId: Int, val reportId: Int) :
        SpotDetailSideEffect
}
