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

package com.netchar.nicknamer.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentMainBinding
import com.netchar.nicknamer.presentation.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(R.layout.fragment_main) {
    override val viewModel by sharedViewModel<MainViewModel>()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe()
    }

    private fun observe() {
        viewModel.toastMessage.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
    }

    fun showHistory() {
        findNavController().navigate(R.id.history_bottom_sheet_dialog_fragment)
    }
}

