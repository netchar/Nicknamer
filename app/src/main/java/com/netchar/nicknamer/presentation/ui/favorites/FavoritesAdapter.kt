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

package com.netchar.nicknamer.presentation.ui.favorites

import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.netchar.nicknamer.databinding.RowFavoriteBinding
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindableViewHolder
import com.netchar.nicknamer.presentation.infrastructure.helpers.DefaultDiffCallback
import com.netchar.nicknamer.presentation.infrastructure.inflater

class FavoritesAdapter(private val listener: Consumer<Nickname>) : BindableListAdapter<Nickname, FavoritesAdapter.FavoriteViewHolder>(DefaultDiffCallback<Nickname>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(RowFavoriteBinding.inflate(parent.inflater(), parent, false).apply {
            handler = listener
        })
    }

    class FavoriteViewHolder(private val binding: RowFavoriteBinding) : BindableViewHolder<Nickname>(binding.root) {
        override fun bind(model: Nickname) {
            binding.name = model
            binding.executePendingBindings()
        }
    }
}

abstract class BindableListAdapter<TListItem, TViewHolder: BindableViewHolder<TListItem>>(callback:  DiffUtil.ItemCallback<TListItem>) : ListAdapter<TListItem, TViewHolder>(callback) {
    override fun onBindViewHolder(holder: TViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}