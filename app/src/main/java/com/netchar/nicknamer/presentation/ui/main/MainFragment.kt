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
import com.netchar.nicknamer.domen.service.NicknameGeneratorService.Config.*
import com.netchar.nicknamer.presentation.infrastructure.copyToClipboard
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsEvent
import com.netchar.nicknamer.presentation.infrastructure.viewBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by sharedViewModel<MainViewModel>(from = {
        findNavController().getViewModelStoreOwner(R.id.nav_graph)
    })
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val analytics by inject<Analytics>()

    private val genderGroupSelector = ViewGroupSelector(
            R.id.main_radio_btn_male to Gender.MALE,
            R.id.main_radio_btn_female to Gender.FEMALE,
    )

    private val alphabetGroupSelector = ViewGroupSelector(
            R.id.main_radio_btn_cyrillic to Alphabet.CYRILLIC,
            R.id.main_radio_btn_latin to Alphabet.LATIN,
    )

    init {
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        analytics.trackScreen(AnalyticsEvent.ViewScreen(AnalyticsEvent.ViewScreen.Screen.MAIN))
        viewModel.updateFavoriteState()
    }

    override fun onResume() {
        super.onResume()
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
            mainTvNickname.copyToClipboard()
            Toast.makeText(requireContext(), R.string.message_copied_to_clipboard, Toast.LENGTH_SHORT).show()
        }

        mainCheckFavorite.setOnClickListener {
            if (mainCheckFavorite.isChecked) {
                viewModel.addToFavorites(mainTvNickname.text.toString())
            } else {
                viewModel.removeFromFavorites(mainTvNickname.text.toString())
            }
        }

        mainBtnGenerate.setOnClickListener {
            viewModel.generateNewNickname()
        }

        mainRadioGrpGender.addOnButtonCheckedListener { group, checkedId, isChecked  ->
            viewModel.setGender(genderGroupSelector[checkedId])
        }

        mainRadioGrpAlphabet.addOnButtonCheckedListener { group, checkedId, isChecked ->
            viewModel.setAlphabet(alphabetGroupSelector[checkedId])
        }

        mainSlideLength.addOnChangeListener { _, value, _ ->
            viewModel.setLength(value)
        }
    }

    private fun observe() = with(viewModel) {
        nickname.observe(viewLifecycleOwner, { nickname ->
            binding.mainTvNickname.text = nickname.value
        })
        gender.observe(viewLifecycleOwner, { gender ->
            binding.mainRadioGrpGender.check(genderGroupSelector[gender])
        })
        alphabet.observe(viewLifecycleOwner, { alphabet ->
            binding.mainRadioGrpAlphabet.check(alphabetGroupSelector[alphabet])
        })
        nicknameLength.observe(viewLifecycleOwner, { length ->
            binding.mainSlideLength.value = length
        })
        isFavorite.observe(viewLifecycleOwner, { isFavorite ->
            binding.mainCheckFavorite.isChecked = isFavorite
        })
    }
}

class ViewGroupSelector<TRadioValue>(vararg pairs: Pair<Int, TRadioValue>) {
    private val idsToValueMap = mapOf(*pairs)
    operator fun get(radioItemId: Int): TRadioValue = idsToValueMap.getValue(radioItemId)
    operator fun get(gender: TRadioValue): Int = idsToValueMap.keys.first { gender == idsToValueMap[it] }
}
