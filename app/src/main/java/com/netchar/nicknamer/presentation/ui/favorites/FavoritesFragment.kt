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

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentFavoritesBinding
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.presentation.infrastructure.copyToClipboard
import com.netchar.nicknamer.presentation.infrastructure.viewBinding
import com.netchar.nicknamer.presentation.infrastructure.visible
import com.netchar.nicknamer.presentation.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : Fragment(R.layout.fragment_favorites), NavController.OnDestinationChangedListener {
    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel by viewModel<FavoritesViewModel>()

    private val favoritesAdapter = FavoritesAdapter { nickname ->
        copyToClipboard(nickname)
    }

    private var snackbar: Snackbar? = null

    private fun copyToClipboard(nickname: Nickname) {
        val context = requireContext()
        context.copyToClipboard(nickname.value)
        Toast.makeText(context, R.string.message_copied_to_clipboard, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bind()
        observe()
    }

    override fun onStart() {
        super.onStart()
        findNavController().addOnDestinationChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        findNavController().removeOnDestinationChangedListener(this)
    }

    private fun observe() {
        viewModel.nicknames.observe(viewLifecycleOwner, { nicknames ->
            updateUI(nicknames)
        })
    }

    private fun updateUI(nicknames: List<Nickname>) {
        favoritesAdapter.submitList(nicknames)
        binding.favoriteLayoutEmptyState.visible(nicknames.isEmpty())
    }

    private fun bind() {
        with(binding.favoriteRecycler) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = favoritesAdapter
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.favoriteRecycler)
    }

    private val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        private val background by lazy {
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.red_600))
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            removeFromFavorites(viewHolder)
            showUndoSnackbar()
        }

        private fun removeFromFavorites(viewHolder: RecyclerView.ViewHolder) {
            val position = viewHolder.adapterPosition
            val nicknameItem = favoritesAdapter.currentList[position]
            viewModel.removeFromFavorites(nicknameItem)
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            val itemView: View = viewHolder.itemView
            when {
                dX > 0 -> background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
                dX < 0 -> background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                else -> background.setBounds(0, 0, 0, 0)
            }
            background.draw(c)
        }
    }

    private fun showUndoSnackbar() {
        val mainActivity = requireActivity() as? MainActivity

        if (mainActivity != null) {
            snackbar = Snackbar.make(binding.favoriteRecycler, getString(R.string.message_remove_back), Snackbar.LENGTH_LONG)
                .setAnchorView(mainActivity.binding.bottomNav)
                .setAction(getString(R.string.button_undo)) { viewModel.restoreFavorites() }
                .also { it.show() }
        }
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id == R.id.main_fragment) {
            viewModel.applyRemoving()
            snackbar?.dismiss()
        }
    }
}

