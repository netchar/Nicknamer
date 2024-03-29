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
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.netchar.nicknamer.R

class PrivacyPolicyDialogFragment : DialogFragment() {
    private val webView by lazy {
        WebView(requireContext()).apply {
            resources.openRawResource(R.raw.privacy_policy)
                .bufferedReader(Charsets.UTF_8)
                .use { inputStream ->
                    loadData(inputStream.readText(), "text/html", "utf-8")
                }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(webView)
            .setPositiveButton(getString(R.string.button_ok), null)
            .create()
    }
}