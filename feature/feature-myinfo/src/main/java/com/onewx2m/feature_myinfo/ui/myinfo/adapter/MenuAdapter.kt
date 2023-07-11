package com.onewx2m.feature_myinfo.ui.myinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewx2m.core_ui.extensions.onThrottleClick
import com.onewx2m.feature_myinfo.R
import com.onewx2m.feature_myinfo.databinding.ItemListViewMyInfoBinding

enum class MyInfoMenu(@StringRes val StringRes: Int) {
    MY_ARTICLE(R.string.my_info_menu_my_article),
    ASK(R.string.my_info_menu_ask),
    BAN_HISTORY(R.string.my_info_menu_ban_history),
    OPEN_SOURCE(R.string.my_info_menu_open_source),
    TERMS(R.string.my_info_menu_terms),
    PERSONAL_INFO_POLICY(R.string.my_info_menu_personal_info_policy),
    COMMUNITY_RULE(R.string.my_info_menu_community_rule),
    LOGOUT(R.string.my_info_menu_logout),
    QUIT(R.string.my_info_menu_quit),
}

class MenuAdapter(private val onItemClick: (MyInfoMenu) -> Unit) :
    ListAdapter<MyInfoMenu, MenuHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val binding = ItemListViewMyInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return MenuHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MyInfoMenu>() {
            override fun areItemsTheSame(
                oldItem: MyInfoMenu,
                newItem: MyInfoMenu,
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: MyInfoMenu,
                newItem: MyInfoMenu,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class MenuHolder(
    private val binding: ItemListViewMyInfoBinding,
    private val onItemClick: (MyInfoMenu) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var menu: MyInfoMenu? = null

    init {
        binding.root.onThrottleClick {
            menu?.let {
                onItemClick(it)
            }
        }
    }

    fun bind(menu: MyInfoMenu) {
        this.menu = menu

        binding.textView.text = binding.root.context.getString(menu.StringRes)
    }
}
