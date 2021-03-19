package com.netchar.nicknamer.presentation.infrastructure.helpers

import android.view.ViewGroup

interface ViewHolderBindingFactory<TModel: BindableViewHolder<*, *>> {
    fun from(parent: ViewGroup) : TModel
}