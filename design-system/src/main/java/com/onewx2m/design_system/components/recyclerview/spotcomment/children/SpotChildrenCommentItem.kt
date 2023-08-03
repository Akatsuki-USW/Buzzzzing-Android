package com.onewx2m.design_system.components.recyclerview.spotcomment.children

import com.onewx2m.design_system.components.recyclerview.spotcomment.SpotCommentType

data class SpotChildrenCommentItem(
    val type: SpotCommentType = SpotCommentType.NORMAL,
    val id: Int = -1,
    val parentId: Int = -1,
    val profileImageUrl: String = "",
    val nickname: String = "",
    val createdAt: String = "",
    val content: String = "",
    val isAuthor: Boolean = false,
)
