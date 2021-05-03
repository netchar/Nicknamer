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
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentFavoritesBinding
import com.netchar.nicknamer.presentation.infrastructure.helpers.SwipeToDeleteCallback
import com.netchar.nicknamer.presentation.ui.BaseFragment
import com.netchar.nicknamer.presentation.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : BaseFragment<FavoritesViewModel, FragmentFavoritesBinding>(R.layout.fragment_favorites), NavController.OnDestinationChangedListener, SwipeToDeleteCallback.Listener {
    private var snackbar: Snackbar? = null

    override val viewModel: FavoritesViewModel by viewModel()

    private val favoritesAdapter = FavoritesAdapter { nickname ->
        viewModel.copyToClipboard(nickname)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBindings()
    }

    override fun onStart() {
        super.onStart()
        findNavController().addOnDestinationChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        findNavController().removeOnDestinationChangedListener(this)
    }

    private fun setupBindings() {
        binding.favoriteRecycler.adapter = favoritesAdapter

        val swipeToDeleteCallback = SwipeToDeleteCallback(requireContext(), this)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.favoriteRecycler)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id != R.id.favorites_fragment) {
            viewModel.applyRemoving()
        }

        snackbar?.dismiss()
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder) {
        removeFromFavorites(viewHolder)
        showUndoSnackbar()
    }

    private fun removeFromFavorites(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        val nicknameItem = favoritesAdapter.currentList[position]
        viewModel.removeFromFavorites(nicknameItem)
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
}