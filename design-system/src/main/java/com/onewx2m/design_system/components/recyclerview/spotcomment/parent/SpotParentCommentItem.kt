package com.onewx2m.design_system.components.recyclerview.spotcomment.parent

import com.onewx2m.design_system.components.recyclerview.spotcomment.SpotCommentType
import com.onewx2m.design_system.components.recyclerview.spotcomment.children.SpotChildrenCommentItem

data class SpotParentCommentItem(
    val type: SpotCommentType = SpotCommentType.NORMAL,
    val id: Int = -1,
    val userId: Int = -1,
    val profileImageUrl: String = "",
    val nickname: String = "",
    val createdAt: String = "",
    val content: String = "",
    val totalChildrenCount: Int = 0,
    val visibleChildrenCommentList: List<SpotChildrenCommentItem> = emptyList(),
    val isAuthor: Boolean = false,
)
