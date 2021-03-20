package com.netchar.nicknamer.presentation.infrastructure.helpers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingViewHolder<TBinding : ViewBinding,TModel>(val binding: TBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: TModel)

    interface Factory<ViewHolder: BindingViewHolder<*, *>> {
        fun from(parent: ViewGroup) : ViewHolder
    }
}