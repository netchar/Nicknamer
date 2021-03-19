package com.netchar.nicknamer.presentation.infrastructure.helpers

import androidx.recyclerview.widget.DiffUtil

class DefaultDiffCallback<ListItem : Any> : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem == newItem
}