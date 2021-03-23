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

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.netchar.nicknamer.databinding.RowContactBinding
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindableViewHolder
import com.netchar.nicknamer.presentation.infrastructure.helpers.DefaultDiffCallback
import com.netchar.nicknamer.presentation.infrastructure.inflater
import com.netchar.nicknamer.presentation.infrastructure.listen

class ContactsAdapter(private val listener: (Contact) -> Unit) : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(DefaultDiffCallback<Contact>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent).listen { position -> listener(getItem(position)) }
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ContactViewHolder(binding: RowContactBinding) : BindableViewHolder<RowContactBinding, Contact>(binding) {
        override fun bind(model: Contact) = with(binding) {
            rowContactImage.setImageResource(model.image)
            rowContactTxtDescription.setText(model.description)
        }

        companion object : Factory<ContactViewHolder> {
            override fun from(parent: ViewGroup) = ContactViewHolder(RowContactBinding.inflate(parent.inflater(), parent, false))
        }
    }
}