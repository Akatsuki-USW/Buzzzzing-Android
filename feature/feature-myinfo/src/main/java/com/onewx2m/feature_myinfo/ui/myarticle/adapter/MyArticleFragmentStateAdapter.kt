package com.onewx2m.feature_myinfo.ui.myarticle.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

enum class MyArticleViewPagerType(val idx: Int) {
    WRITTEN(0), COMMENTED(1);

    companion object {
        fun getType(idx: Int): MyArticleViewPagerType {
            return MyArticleViewPagerType.values().find {
                it.idx == idx
            } ?: WRITTEN
        }
    }
}

class MyArticleFragmentStateAdapter(fragment: Fragment, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = MyArticleViewPagerType.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        MyArticleViewPagerType.WRITTEN.idx -> fragmentList[position]

        MyArticleViewPagerType.COMMENTED.idx -> fragmentList[position]

        else -> throw IllegalAccessException()
    }
}
