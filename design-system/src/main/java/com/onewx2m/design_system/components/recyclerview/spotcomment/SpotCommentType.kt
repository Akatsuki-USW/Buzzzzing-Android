package com.onewx2m.design_system.components.recyclerview.spotcomment

import com.onewx2m.design_system.enum.ItemViewType

enum class SpotCommentType(val idx: Int) {
    NORMAL(0), DELETE(1), LOADING(2);

    companion object {
        fun valueOf(value: Int): SpotCommentType {
            return SpotCommentType.values().find {
                it.idx == value
            } ?: SpotCommentType.LOADING
        }
    }
}
