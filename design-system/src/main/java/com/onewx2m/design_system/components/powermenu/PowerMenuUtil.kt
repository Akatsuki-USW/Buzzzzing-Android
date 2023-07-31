package com.onewx2m.design_system.components.powermenu

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.onewx2m.core_ui.extensions.px
import com.skydoves.powermenu.CustomPowerMenu
import com.skydoves.powermenu.OnMenuItemClickListener

private const val MENU_RADIUS = 10
private const val BACKGROUND_ALPHA = 0f
private const val MENU_WIDTH = 98
private const val MENU_PADDING = 10

private fun showPowerMenu(
    context: Context,
    view: View,
    xOffset: Int,
    yOffset: Int,
    lifecycleOwner: LifecycleOwner,
    items: List<String>,
    onMenuItemClickListener: OnMenuItemClickListener<String>,
) {
    CustomPowerMenu.Builder<String, CustomPopUpMenuAdapter>(
        context,
        CustomPopUpMenuAdapter(),
    )
        .addItemList(items)
        .setMenuRadius(MENU_RADIUS.px.toFloat())
        .setBackgroundAlpha(BACKGROUND_ALPHA)
        .setWidth(MENU_WIDTH.px)
        .setPadding(MENU_PADDING.px)
        .setLifecycleOwner(lifecycleOwner)
        .setAutoDismiss(true)
        .setOnMenuItemClickListener(onMenuItemClickListener).build().showAsDropDown(
            view,
            xOffset,
            yOffset,
        )
}

fun View.showPowerMenu(
    xOffset: Int,
    yOffset: Int,
    lifecycleOwner: LifecycleOwner,
    items: List<String>,
    onMenuItemClickListener: OnMenuItemClickListener<String>,
) {
    showPowerMenu(
        context = context,
        view = this,
        xOffset = xOffset,
        yOffset = yOffset,
        lifecycleOwner = lifecycleOwner,
        items = items,
        onMenuItemClickListener = onMenuItemClickListener,
    )
}

enum class PowerMenuType(val kor: String) {
    EDIT("수정하기"), DELETE("삭제하기"), REPORT("신고하기"), BLOCK("차단하기"), REPLY("답글달기");

    companion object {
        fun typeOf(kor: String): PowerMenuType {
            return PowerMenuType.values().find {
                it.kor == kor
            } ?: PowerMenuType.EDIT
        }
    }
}
