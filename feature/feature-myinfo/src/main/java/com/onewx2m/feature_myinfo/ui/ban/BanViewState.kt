package com.onewx2m.feature_myinfo.ui.ban

import com.onewx2m.design_system.components.recyclerview.ban.BanItem
import com.onewx2m.design_system.enum.ItemViewType
import com.onewx2m.domain.model.Ban
import com.onewx2m.mvi.ViewState

data class BanViewState(
    val banList: List<BanItem> = emptyList(),
) : ViewState

internal fun List<Ban>.toItem() = this.mapIndexed { index, ban ->
    BanItem(
        id = index,
        title = ban.title,
        body = ban.content,
        banStartAt = ban.banStartAt,
        banEndedAt = ban.banEndedAt,
    )
}
