package com.onewx2m.design_system.enum

enum class ItemViewType(val idx: Int) {
    NORMAL(0), LOADING(1);

    companion object {
        fun valueOf(value: Int): ItemViewType {
            return ItemViewType.values().find {
                it.idx == value
            } ?: LOADING
        }
    }
}
