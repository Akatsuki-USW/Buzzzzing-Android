package com.onewx2m.feature_home.ui.locationdetail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.onewx2m.feature_home.ui.locationdetail.historicaldata.HistoricalDataFragment

enum class LocationDetailViewPagerType(val idx: Int) {
    HISTORICAL_DATE(0), RECOMMEND_PLACE_LIST(1);

    companion object {
        fun getType(idx: Int): LocationDetailViewPagerType {
            return LocationDetailViewPagerType.values().find {
                it.idx == idx
            } ?: HISTORICAL_DATE
        }
    }
}

class LocationDetailFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = LocationDetailViewPagerType.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        LocationDetailViewPagerType.HISTORICAL_DATE.idx -> HistoricalDataFragment()
        LocationDetailViewPagerType.RECOMMEND_PLACE_LIST.idx -> HistoricalDataFragment()
        else -> throw IllegalAccessException()
    }
}
