package com.netchar.nicknamer.presentation.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentAboutBinding
import com.netchar.nicknamer.presentation.viewBinding
import org.koin.android.ext.android.inject

class AboutFragment : Fragment(R.layout.fragment_about) {
    private val viewModel by inject<AboutViewModel>()
    private val viewBinding by viewBinding(FragmentAboutBinding::bind)
    private val adapter = ContactsAdapter()

    private val navigationController by lazy {
        findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViews()
        observe()
    }

    private fun bindViews() = with(viewBinding) {
        aboutTxtExternalLibrariesLicences.setOnClickListener {
            navigationController.navigate(R.id.license_dialog_fragment)
        }
        aboutTxtPrivacyPolicy.setOnClickListener {
            navigationController.navigate(R.id.privacy_policy_fragment)
        }
        aboutTxtVersion.text = viewModel.buildVersion
        aboutRecyclerContacts.adapter = adapter
    }

    private fun observe() {
        viewModel.contacts.observe(viewLifecycleOwner, { contacts ->
            adapter.submitList(contacts)
        })
    }
}

