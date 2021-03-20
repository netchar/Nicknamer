package com.netchar.nicknamer.presentation.about

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.netchar.nicknamer.databinding.RowContactBinding
import com.netchar.nicknamer.presentation.inflater
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindingViewHolder
import com.netchar.nicknamer.presentation.infrastructure.helpers.DefaultDiffCallback

class ContactsAdapter : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(DefaultDiffCallback<Contact>()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ContactViewHolder(binding: RowContactBinding) : BindingViewHolder<RowContactBinding, Contact>(binding) {
        override fun bind(model: Contact) = with(binding) {
            rowContactImage.setImageResource(model.image)
            rowContactTxtDescription.setText(model.description)
        }

        companion object : Factory<ContactViewHolder> {
            override fun from(parent: ViewGroup) = ContactViewHolder(RowContactBinding.inflate(parent.inflater()))
        }
    }
}