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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentFavoritesBinding
import com.netchar.nicknamer.presentation.infrastructure.copyToClipboard
import com.netchar.nicknamer.presentation.infrastructure.viewBinding
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel by inject<FavoritesViewModel>()
    private val favoritesAdapter = FavoritesAdapter {
        requireContext().run {
            copyToClipboard(it.value)
            Toast.makeText(this, R.string.message_copied_to_clipboard, Toast.LENGTH_SHORT).show()
        }
    }

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


}

