package com.netchar.nicknamer.presentation.about

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.netchar.nicknamer.databinding.RowLibraryBinding
import com.netchar.nicknamer.presentation.inflater
import com.netchar.nicknamer.presentation.infrastructure.LibrariesProvider.Library
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindableViewHolder
import com.netchar.nicknamer.presentation.infrastructure.helpers.DefaultDiffCallback
import com.netchar.nicknamer.presentation.infrastructure.helpers.ViewHolderBindingFactory
import com.netchar.nicknamer.presentation.listen

class LicenceAdapter(private val onClick: (library: Library) -> Unit) : ListAdapter<Library, LicenceAdapter.LibraryViewHolder>(DefaultDiffCallback<Library>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder.from(parent).listen { position, _ ->
            onClick(getItem(position))
        }
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class LibraryViewHolder private constructor(binding: RowLibraryBinding) : BindableViewHolder<RowLibraryBinding, Library>(binding) {

        override fun bind(model: Library) = with(binding) {
            rowLibraryTitle.text = model.name
            rowLibraryDescription.text = model.description
        }

        companion object : ViewHolderBindingFactory<LibraryViewHolder> {
            override fun from(parent: ViewGroup): LibraryViewHolder {
                val binding = RowLibraryBinding.inflate(parent.inflater(), parent, false)
                return LibraryViewHolder(binding)
            }
        }
    }
}

