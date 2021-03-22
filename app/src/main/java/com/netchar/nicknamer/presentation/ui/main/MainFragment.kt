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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentMainBinding
import com.netchar.nicknamer.presentation.copyToClipboard
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsEvent
import com.netchar.nicknamer.presentation.viewBinding
import org.koin.android.ext.android.inject

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by inject<MainViewModel>()
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val analytics by inject<Analytics>()

    init {
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        analytics.trackScreen(AnalyticsEvent.ViewScreen(AnalyticsEvent.ViewScreen.Screen.MAIN))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
    }

    private fun bindViews() = with(binding) {
        mainTvNickname.setOnClickListener {
            analytics.trackEvent(AnalyticsEvent.Event("copy_to_clipboard"))
            copyToClipboard()
        }

        mainBtnGenerate.setOnClickListener {
            viewModel.generateNewNickname()
        }

        mainRadioGrpGender.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setGender(checkedId)
        }

        mainRadioGrpAlphabet.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setAlphabet(checkedId)
        }

        mainSlideLength.addOnChangeListener { _, value, _ ->
            viewModel.setLength(value)
        }
    }

    private fun observe() {
        viewModel.nickname.observe(viewLifecycleOwner, { nickname ->
            binding.mainTvNickname.text = nickname.value
        })
    }

    private fun copyToClipboard() {
        val context = requireContext()
        context.copyToClipboard(binding.mainTvNickname.text)
        Toast.makeText(context, getString(R.string.message_copied_to_clipboard), Toast.LENGTH_SHORT).show()
    }
}

