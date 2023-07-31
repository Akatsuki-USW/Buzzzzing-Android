package com.onewx2m.recommend_place.ui.spotdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.onewx2m.recommend_place.R
import com.onewx2m.recommend_place.databinding.ItemViewPager2ImageBinding

class SpotDetailImageViewAdapter(private val imageUrls: List<String>) :
    RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding =
            ItemViewPager2ImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun getItemCount(): Int = imageUrls.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.imageView.load(imageUrls[position]) {
            crossfade(true)
            placeholder(com.onewx2m.design_system.R.drawable.bg_solid_gray07_rounded_5)
        }
    }
}

class PagerViewHolder(private val binding: ItemViewPager2ImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val imageView = binding.imageView
}
