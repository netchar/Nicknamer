package com.netchar.nicknamer.presentation.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.netchar.nicknamer.databinding.RowContactBinding
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindableViewHolder

class ContactsAdapter() : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(ContactDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = RowContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ContactViewHolder(private val binding: RowContactBinding) : BindableViewHolder<Contact>(binding) {
        override fun bind(model: Contact) = with(binding) {
            rowContactImage.setImageResource(model.image)
            rowContactTxtDescription.setText(model.description)
        }
    }

    class ContactDiff : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Contact, newItem: Contact) = oldItem == newItem
    }
}