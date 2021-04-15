/*
 * Copyright (c) 2021 Eugene Glushankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.netchar.nicknamer.presentation.infrastructure.binding.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindingAdapters {
    @BindingAdapter("items")
    @JvmStatic
    fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: List<T>) {
        val adapter = recyclerView.adapter ?: return

        @Suppress("UNCHECKED_CAST")
        (adapter as? ListAdapter<T, *>)?.submitList(data)
    }

    @BindingAdapter("hasFixedSize")
    @JvmStatic
    fun setHasFixedSize(recyclerView: RecyclerView, hasFixedSize: Boolean) {
        recyclerView.setHasFixedSize(hasFixedSize)
    }

    @BindingAdapter("hasVerticalDividers")
    @JvmStatic
    fun setVerticalDividers(recyclerView: RecyclerView, hasVerticalDividers: Boolean) {
        if (hasVerticalDividers) {
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        }
    }
}