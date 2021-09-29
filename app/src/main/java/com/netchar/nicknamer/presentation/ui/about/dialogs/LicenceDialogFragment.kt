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

package com.netchar.nicknamer.presentation.ui.about.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.util.Consumer
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.RowLibraryBinding
import com.netchar.nicknamer.presentation.infrastructure.ExternalAppService
import com.netchar.nicknamer.presentation.infrastructure.LibrariesProvider
import com.netchar.nicknamer.presentation.infrastructure.LibrariesProvider.Library
import com.netchar.nicknamer.presentation.infrastructure.inflater
import org.koin.android.ext.android.inject

class LicenceDialogFragment : DialogFragment() {
    private val externalAppService: ExternalAppService by inject()
    private val librariesProvider: LibrariesProvider by inject()

    private val adapter by lazy {
        LibsAdapter(requireContext(), librariesProvider.getLibraries()) { library ->
            externalAppService.openWebPage(library.link)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.about_label_libraries))
            setAdapter(adapter, null)
            setPositiveButton(getString(R.string.button_ok), null)
        }

        return builder.create()
    }

    private class LibsAdapter(
            context: Context,
            libraries: List<Library>,
            private val listener: Consumer<Library>
    ) : ArrayAdapter<Library>(context, 0, libraries) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding = getBinding(convertView, parent).apply {
                library = requireNotNull(getItem(position))
                handler = listener
                executePendingBindings()
            }

            return binding.root
        }

        private fun getBinding(convertView: View?, parent: ViewGroup): RowLibraryBinding {
            if (convertView == null) {
                return RowLibraryBinding.inflate(parent.inflater(), parent, false)
            }

            return requireNotNull(DataBindingUtil.getBinding(convertView))
        }
    }
}

