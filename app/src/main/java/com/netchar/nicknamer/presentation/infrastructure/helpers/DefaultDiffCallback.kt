package com.netchar.nicknamer.presentation.infrastructure.helpers

import androidx.recyclerview.widget.DiffUtil

class DefaultDiffCallback<ListItem> : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}