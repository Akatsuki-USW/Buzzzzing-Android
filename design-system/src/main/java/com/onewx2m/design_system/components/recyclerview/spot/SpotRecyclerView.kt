package com.onewx2m.design_system.components.recyclerview.spot

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewx2m.core_ui.extensions.infiniteScrolls
import com.onewx2m.design_system.databinding.RecyclerViewSpotBinding

class SpotRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: RecyclerViewSpotBinding
    private var spotAdapter: SpotAdapter? = null

    fun submitList(data: List<SpotItem>) {
        spotAdapter?.submitList(data)
    }

    fun initAdapter(
        congestion: String = "",
        onItemClick: (Int) -> Unit = {},
        onBookmarkClick: (Int) -> Unit = {},
        infiniteScrolls: () -> Unit = {},
    ) {
        if (spotAdapter != null) return
        spotAdapter = SpotAdapter(congestion, onItemClick, onBookmarkClick)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = spotAdapter
            infiniteScrolls {
                infiniteScrolls()
            }
        }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RecyclerViewSpotBinding.inflate(inflater, this, true)
    }
}
