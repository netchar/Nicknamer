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
import androidx.recyclerview.widget.ListAdapter
import com.netchar.nicknamer.databinding.RowFavoriteBinding
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindableViewHolder
import com.netchar.nicknamer.presentation.infrastructure.helpers.DefaultDiffCallback
import com.netchar.nicknamer.presentation.infrastructure.inflater
import com.netchar.nicknamer.presentation.infrastructure.listen

class FavoritesAdapter(private val listener: (Nickname) -> Unit) : ListAdapter<Nickname, FavoritesAdapter.FavoriteViewHolder>(DefaultDiffCallback<Nickname>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(RowFavoriteBinding.inflate(parent.inflater(), parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item: Nickname = getItem(position)
        holder.bind(item)
    }

    inner class FavoriteViewHolder(private val binding: RowFavoriteBinding) : BindableViewHolder<Nickname>(binding.root) {
        override fun bind(model: Nickname) {
            binding.favoriteTxtNickname.text = model.value
            binding.favoriteRowImgCopy.setOnClickListener { listener(currentList[adapterPosition]) }
        }
    }
}