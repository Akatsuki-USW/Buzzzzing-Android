package com.onewx2m.recommend_place.ui.spotdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.loadUrl
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.recommend_place.R
import com.onewx2m.recommend_place.databinding.ItemRecyclerViewSpotDetailContentBinding

data class SpotDetailContentItem(
    val spotId: Int = -1,
    val profileImageUrl: String = "",
    val nickname: String = "",
    val createdAt: String = "",
    val isBookmarked: Boolean = false,
    val title: String = "",
    val location: String = "",
    val imageUrls: List<String> = emptyList(),
    val content: String = "",
    val commentCount: Int = 0,
)

class SpotDetailContentAdapter(
    private val onBookmarkClick: (Int) -> Unit = {},
) :
    RecyclerView.Adapter<SpotDetailContentHolder>() {

    private var item: SpotDetailContentItem = SpotDetailContentItem()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotDetailContentHolder {
        val binding =
            ItemRecyclerViewSpotDetailContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return SpotDetailContentHolder(binding, onBookmarkClick)
    }

    override fun onBindViewHolder(holder: SpotDetailContentHolder, position: Int) {
        holder.bind(item)
    }

    override fun getItemCount(): Int = 1

    fun setData(item: SpotDetailContentItem) {
        this.item = item
        notifyItemChanged(0)
    }
}

class SpotDetailContentHolder(
    private val binding: ItemRecyclerViewSpotDetailContentBinding,
    private val onBookmarkClick: (Int) -> Unit = {},
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val PROFILE_IMAGE_RADIUS = 6
    }

    private var spotId = -1

    init {
        binding.imageViewBookmark.onThrottleClick {
            onBookmarkClick(spotId)
        }
    }

    fun bind(
        item: SpotDetailContentItem,
    ) {
        spotId = item.spotId

        val bookmarkColor = ContextCompat.getColor(
            binding.root.context,
            if (item.isBookmarked) com.onewx2m.design_system.R.color.gray01 else com.onewx2m.design_system.R.color.gray06,
        )

        binding.apply {
            imageViewProfile.loadUrl(
                item.profileImageUrl,
                com.onewx2m.design_system.R.drawable.ic_profile,
                PROFILE_IMAGE_RADIUS,
            )
            textViewNickname.text = item.nickname
            textViewDate.text = item.createdAt
            textViewTitle.text = item.title
            textViewLocation.text = item.location
            if (item.imageUrls.isNotEmpty()) {
                viewPager2.apply {
                    adapter = SpotDetailImageViewAdapter(item.imageUrls)
                    visibility = View.VISIBLE
                }
            } else {
                viewPager2.visibility = View.GONE
            }
            imageViewBookmark.setColorFilter(bookmarkColor)
            textViewContent.text = item.content
            textViewCommentCount.text = root.context.getString(
                R.string.fragment_spot_detail_comment_count,
                item.commentCount,
            )
        }
    }
}
