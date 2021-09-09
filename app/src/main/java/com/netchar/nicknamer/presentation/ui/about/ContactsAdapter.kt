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
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.util.Consumer
import androidx.recyclerview.widget.ListAdapter
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.RowContactBinding
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindableViewHolder
import com.netchar.nicknamer.presentation.infrastructure.helpers.DefaultDiffCallback
import com.netchar.nicknamer.presentation.infrastructure.inflater

class ContactsAdapter(private val listener: Consumer<Contact>) : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(DefaultDiffCallback<Contact>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(RowContactBinding.inflate(parent.inflater(), parent, false).apply {
            handler = listener
        })
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ContactViewHolder(private val binding: RowContactBinding) : BindableViewHolder<Contact>(binding.root) {
        override fun bind(model: Contact) {
            binding.contact = model
            binding.executePendingBindings()
        }
    }
}

sealed class Contact(@DrawableRes val image: Int, @StringRes val description: Int) {
    object LinkedIn : Contact(R.drawable.ic_linkedin, R.string.about_label_connect_on_linked_id)
    object Mail : Contact(R.drawable.ic_gmail, R.string.about_label_send_email)
}