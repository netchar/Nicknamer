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

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentFavoritesBinding
import com.netchar.nicknamer.databinding.RowFavoriteBinding
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.presentation.infrastructure.copyToClipboard
import com.netchar.nicknamer.presentation.infrastructure.inflater
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindableViewHolder
import com.netchar.nicknamer.presentation.infrastructure.helpers.DefaultDiffCallback
import com.netchar.nicknamer.presentation.infrastructure.listen
import com.netchar.nicknamer.presentation.infrastructure.viewBinding
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel by inject<FavoritesViewModel>()
    private val favoritesAdapter = FavoritesAdapter()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bind()
        observe()
    }

    private fun observe() {
        viewModel.nicknames.observe(viewLifecycleOwner, { nicknames ->
            favoritesAdapter.submitList(nicknames)
        })
    }

    private fun bind() {
        with(binding.favoriteRecycler) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = favoritesAdapter
        }
    }

    class FavoritesAdapter() : ListAdapter<Nickname, FavoritesAdapter.FavoriteViewHolder>(DefaultDiffCallback<Nickname>()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
            return FavoriteViewHolder.from(parent).listen { position ->
//                val nickname = getItem(position).value
//                onClickListener(nickname)
                val item: Nickname = getItem(position)
                val t = 0
            }
        }

        override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
            val item: Nickname = getItem(position)
            holder.bind(item)
        }

        class FavoriteViewHolder(binding: RowFavoriteBinding) : BindableViewHolder<RowFavoriteBinding, Nickname>(binding) {
            override fun bind(model: Nickname) {
                binding.favoriteTxtNickname.text = model.value
            }

            companion object : Factory<FavoriteViewHolder> {
                override fun from(parent: ViewGroup) = FavoriteViewHolder(RowFavoriteBinding.inflate(parent.inflater(), parent, false))
            }
        }
    }
}