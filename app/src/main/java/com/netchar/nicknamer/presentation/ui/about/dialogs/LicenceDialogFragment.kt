package com.netchar.nicknamer.presentation.ui.about.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.netchar.nicknamer.R
import com.netchar.nicknamer.presentation.inflate
import com.netchar.nicknamer.presentation.infrastructure.ExternalAppService
import com.netchar.nicknamer.presentation.infrastructure.LibrariesProvider
import com.netchar.nicknamer.presentation.infrastructure.LibrariesProvider.Library
import org.koin.android.ext.android.inject

class LicenceDialogFragment : DialogFragment() {
    private val externalAppService: ExternalAppService by inject()
    private val librariesProvider: LibrariesProvider by inject()

    private val adapter by lazy {
        LibAdapter(requireContext(), librariesProvider.getLibraries()) { library ->
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

    private class LibAdapter(context: Context, libraries: List<Library>, private val listener: (Library) -> Unit) : ArrayAdapter<Library>(context, 0, libraries) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val library = requireNotNull(getItem(position))
            val view = convertView ?: inflateView(parent, library)

            return view.apply {
                findViewById<TextView>(R.id.row_library_title).text = library.name
                findViewById<TextView>(R.id.row_library_description).text = library.description
            }
        }

        private fun inflateView(parent: ViewGroup, library: Library) : View {
            return parent.inflate(R.layout.row_library).apply {
                setOnClickListener { listener(library) }
            }
        }
    }
}

