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
import android.view.View
import androidx.navigation.fragment.findNavController
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentAboutBinding
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsEvent
import com.netchar.nicknamer.presentation.ui.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : BaseFragment<AboutViewModel, FragmentAboutBinding>(R.layout.fragment_about) {
    override val viewModel: AboutViewModel by viewModel()
    private val analytics: Analytics by inject()

    private val adapter = ContactsAdapter { contact ->
        analytics.trackEvent(AnalyticsEvent.SelectContact(contact))
        viewModel.openContact(contact)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.handler = this
        binding.aboutRecyclerContacts.adapter = adapter
    }

    fun navigate(selectedItem: String, destinationId: Int) {
        analytics.trackEvent(AnalyticsEvent.SelectItem(selectedItem))

        val navController = findNavController()
        navController.navigate(destinationId)
    }
}
