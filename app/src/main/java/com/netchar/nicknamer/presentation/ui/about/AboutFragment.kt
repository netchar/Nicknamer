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

package com.netchar.nicknamer.presentation.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentAboutBinding
import com.netchar.nicknamer.presentation.infrastructure.analytics.*
import com.netchar.nicknamer.presentation.infrastructure.viewBinding
import org.koin.android.ext.android.inject

class AboutFragment : Fragment(R.layout.fragment_about) {
    private val viewModel by inject<AboutViewModel>()
    private val viewBinding by viewBinding(FragmentAboutBinding::bind)
    private val analytics by inject<Analytics>()

    private val adapter = ContactsAdapter { contact ->
        analytics.trackEvent(AnalyticsEvent.SelectContact(contact))
        viewModel.openContact(contact)
    }

    override fun onStart() {
        super.onStart()
        analytics.trackScreen(AnalyticsEvent.ViewScreen(AnalyticsEvent.ViewScreen.Screen.ABOUT))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViews()
        observe()
    }

    private fun bindViews() = with(viewBinding) {
        val navController = findNavController()
        aboutTxtExternalLibrariesLicences.setOnClickListener {
            analytics.trackEvent(AnalyticsEvent.SelectItem(aboutTxtExternalLibrariesLicences.text.toString()))
            navController.navigate(R.id.license_dialog_fragment)
        }
        aboutTxtPrivacyPolicy.setOnClickListener {
            analytics.trackEvent(AnalyticsEvent.SelectItem(aboutTxtPrivacyPolicy.text.toString()))
            navController.navigate(R.id.privacy_policy_fragment)
        }
        aboutTxtVersion.text = getString(R.string.about_label_version, viewModel.buildVersion)
        aboutRecyclerContacts.adapter = adapter
    }

    private fun observe() {
        viewModel.contacts.observe(viewLifecycleOwner, { contacts ->
            adapter.submitList(contacts)
        })
    }
}

