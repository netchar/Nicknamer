package com.netchar.nicknamer.presentation.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentAboutBinding
import com.netchar.nicknamer.presentation.viewBinding

class AboutFragment : Fragment(R.layout.fragment_about) {
    private val viewModel by viewModels<AboutViewModel> { AboutViewModelFactory() }
    private val viewBinding by viewBinding(FragmentAboutBinding::bind)
    private val adapter = ContactsAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViews()
        observe()
    }

    private fun bindViews() {
        viewBinding.aboutTxtVersion.text = viewModel.buildVersion
        viewBinding.aboutRecyclerContacts.adapter = adapter
    }

    private fun observe() {
        viewModel.contacts.observe(viewLifecycleOwner, { contacts ->
            adapter.submitList(contacts)
        })
    }
}

