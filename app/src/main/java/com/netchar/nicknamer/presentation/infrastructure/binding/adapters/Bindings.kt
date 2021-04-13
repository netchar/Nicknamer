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

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("visible")
fun visible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("items")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: List<T>) {
    val adapter = recyclerView.adapter ?: return

    @Suppress("UNCHECKED_CAST")
    (adapter as? ListAdapter<T, *>)?.submitList(data)
}

@BindingAdapter("hasFixedSize")
fun setHasFixedSize(recyclerView: RecyclerView, hasFixedSize: Boolean) {
    recyclerView.setHasFixedSize(hasFixedSize)
}

@BindingAdapter("hasVerticalDeividers")
fun <T> setVerticalDividers(recyclerView: RecyclerView, hasVerticalDividers: Boolean) {
    if (hasVerticalDividers) {
        DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
    }
}

@BindingAdapter("toastMessage")
fun toast(view: View, @StringRes messageRes: Int?) {
    if (messageRes == null || messageRes == 0) {
        return
    }

    toast(view, view.context.getString(messageRes))
}

@BindingAdapter("toastMessage")
fun toast(view: View, message: String?) {
    if (message.isNullOrEmpty()) {
        return
    }

    Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
}