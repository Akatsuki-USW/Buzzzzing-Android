package com.onewx2m.feature_bookmark.ui.bookmark.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.onewx2m.feature_bookmark.ui.bookmark.spot.BookmarkSpotFragment

enum class BookmarkViewPagerType(val idx: Int) {
    BUZZZZING(0), SPOT(1);

    companion object {
        fun getType(idx: Int): BookmarkViewPagerType {
            return BookmarkViewPagerType.values().find {
                it.idx == idx
            } ?: BUZZZZING
        }
    }
}

class BookmarkFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = BookmarkViewPagerType.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        BookmarkViewPagerType.BUZZZZING.idx -> BookmarkSpotFragment()

        BookmarkViewPagerType.SPOT.idx -> BookmarkSpotFragment()

        else -> throw IllegalAccessException()
    }
}