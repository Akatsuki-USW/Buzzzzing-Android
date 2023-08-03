package com.onewx2m.recommend_place.ui.spotdetail

import com.onewx2m.design_system.components.recyclerview.spotcomment.SpotCommentType
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem
import com.onewx2m.design_system.components.recyclerview.spotcomment.parent.SpotParentCommentItem
import com.onewx2m.domain.model.SpotComment
import com.onewx2m.domain.model.SpotDetail
import com.onewx2m.mvi.ViewState
import com.onewx2m.recommend_place.ui.spotdetail.adapter.SpotDetailContentItem

data class SpotDetailViewState(
    val spotDetailContent: SpotDetailContentItem = SpotDetailContentItem(),
    val spotCommentList: List<SpotParentCommentItem> = emptyList(),
    val isLoadingLottieVisible: Boolean = true,
    val isRecyclerViewVisible: Boolean = false,
    val isSmallLottieVisible: Boolean = false,
    val isReplyLayoutVisible: Boolean = false,
    val replyNickname: String = "",
    val commentContent: String = "",
    val needCommentRender: Boolean = false,
) : ViewState

internal fun SpotComment.toSpotParentCommentItem() = SpotParentCommentItem(
    type = if (presence) SpotCommentType.NORMAL else SpotCommentType.DELETE,
    id = id,
    userId = userId,
    profileImageUrl = userProfileImageUrl,
    nickname = userNickname,
    createdAt = createdAt,
    content = content,
    totalChildrenCount = childCount,
    isAuthor = isAuthor,
)

internal fun SpotComment.toSpotChildrenCommentItem(parentId: Int) = SpotChildrenCommentItem(
    type = if (presence) SpotCommentType.NORMAL else SpotCommentType.DELETE,
    id = id,
    userId = userId,
    parentId = parentId,
    profileImageUrl = userProfileImageUrl,
    nickname = userNickname,
    createdAt = createdAt,
    content = content,
    isAuthor = isAuthor,
)

internal fun SpotDetail.toSpotDetailContentItem() = SpotDetailContentItem(
    spotId = id,
    profileImageUrl = userProfileImageUrl ?: "",
    nickname = userNickname,
    createdAt = createdAt,
    isBookmarked = isBookmarked,
    title = title,
    location = locationName,
    address = address,
    imageUrls = imageUrls,
    content = content,
    commentCount = 0, // TODO 업데이트
)
