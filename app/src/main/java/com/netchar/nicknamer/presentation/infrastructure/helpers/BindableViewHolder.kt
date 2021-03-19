package com.netchar.nicknamer.presentation.infrastructure.helpers

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindableViewHolder<TModel>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: TModel)
}