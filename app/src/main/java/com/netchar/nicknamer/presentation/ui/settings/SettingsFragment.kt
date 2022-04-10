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

package com.netchar.nicknamer.presentation.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.netchar.nicknamer.R
import com.netchar.nicknamer.presentation.di.Modules
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    private val languageKey = getString(R.string.preference_option_key_language)
    private val themeKey = getString(R.string.preference_option_key_theme)

    private lateinit var languagePref: ListPreference
    private lateinit var themePref: ListPreference

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.bindToAppWidePrefs()
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languagePref = requireNotNull(findPreference(languageKey))
        themePref = requireNotNull(findPreference(themeKey))
        setCurrentValues()
        observe()
    }

    private fun setCurrentValues() {
        val currentLanguage = viewModel.getCurrentLanguage()
        val currentTheme = viewModel.getCurrentTheme()

        languagePref.value = currentLanguage
        themePref.value = currentTheme.toString()
    }

    private fun observe() {
        viewModel.newLanguageSet.observe(viewLifecycleOwner) {
            requireActivity().recreate()
        }
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
    }

    override fun onStop() {
        super.onStop()
        clearListeners()
    }

    private fun setupListeners() {
        languagePref.onPreferenceChangeListener = this
        themePref.onPreferenceChangeListener = this
    }

    private fun clearListeners() {
        languagePref.onPreferenceChangeListener = null
        themePref.onPreferenceChangeListener = null
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        when (preference.key) {
            languageKey -> viewModel.applyNewLanguage(newValue)
            themeKey -> viewModel.applyDayNight(newValue)
        }

        return true
    }

    companion object {
        private fun PreferenceManager.bindToAppWidePrefs() {
            sharedPreferencesName = Modules.PREFERENCES_NAME
            sharedPreferencesMode = Context.MODE_PRIVATE
        }
    }
}