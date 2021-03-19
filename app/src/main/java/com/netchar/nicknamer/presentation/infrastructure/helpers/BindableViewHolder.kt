package com.netchar.nicknamer.presentation.infrastructure.helpers

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindableViewHolder<TBinding : ViewBinding,TModel>(val binding: TBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: TModel)
}