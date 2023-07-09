package com.onewx2m.design_system.components.recyclerview.buzzzzingsmall

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.design_system.databinding.RecyclerViewBuzzzzingSmallBinding

class BuzzzzingSmallRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: RecyclerViewBuzzzzingSmallBinding
    private val buzzzzingSmallAdapter: BuzzzzingSmallAdapter

    var onItemClick: (Int) -> Unit = {}
    var onBookmarkClick: (Int) -> Unit = {}
    var infiniteScrolls: () -> Unit = {}

    fun submitList(data: List<BuzzzzingSmallItem>) {
        buzzzzingSmallAdapter.submitList(data)
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RecyclerViewBuzzzzingSmallBinding.inflate(inflater, this, true)

        buzzzzingSmallAdapter = BuzzzzingSmallAdapter(onItemClick, onBookmarkClick)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = buzzzzingSmallAdapter
            infiniteScrolls {
                infiniteScrolls()
            }
        }
    }
}
