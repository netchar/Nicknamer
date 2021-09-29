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

import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.netchar.nicknamer.R
import com.netchar.nicknamer.presentation.infrastructure.binding.adapters.toast
import com.netchar.nicknamer.presentation.infrastructure.localization.LocalizationHelper
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.lang.Exception
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {
    private val localizationHelper: LocalizationHelper by inject()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        findPreference<ListPreference>(LANGUAGE_PREF_KEY)?.setOnPreferenceChangeListener { _, language ->
            applyNewLanguage(language)
        }
    }

    private fun applyNewLanguage(newLanguage: Any): Boolean {
        try {
            localizationHelper.changeLocale(requireContext(), Locale(newLanguage.toString()))
        } catch (ex: Exception) {
            toast(requireView(), "Unable to apply language. English set as default")
            Timber.e(ex)
        }

        return true
    }

    companion object {
        const val LANGUAGE_PREF_KEY = "preference_key_language"
    }
}