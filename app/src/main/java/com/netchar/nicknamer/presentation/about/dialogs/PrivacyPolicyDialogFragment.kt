package com.netchar.nicknamer.presentation.about.dialogs

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
        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(webView)
            setPositiveButton(getString(R.string.button_ok), null)
        }
        return builder.create()
    }
}