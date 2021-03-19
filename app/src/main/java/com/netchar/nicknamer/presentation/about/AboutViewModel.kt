package com.netchar.nicknamer.presentation.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.netchar.common.services.LibrariesProvider
import com.netchar.nicknamer.presentation.infrastructure.BuildConfiguration

class AboutViewModel(
    private val thirdPartyAppLibrariesProvider: LibrariesProvider,
    private val buildConfiguration: BuildConfiguration
) : ViewModel() {
    private val mutableContacts = MutableLiveData<List<Contact>>()
    private val mutableLibraries = MutableLiveData<List<LibrariesProvider.Library>>()

    val contacts: LiveData<List<Contact>> = mutableContacts
    val libraries: LiveData<List<LibrariesProvider.Library>> = mutableLibraries

    init {
        mutableContacts.value = listOf(
            Contact.Instagram,
            Contact.LinkedIn,
            Contact.Mail,
        )

        mutableLibraries.value = thirdPartyAppLibrariesProvider.getLibraries()
    }

    val buildVersion: String get() = buildConfiguration.getVersionName()

    fun openContact(contact: Contact) {
        when (contact) {
            Contact.Instagram -> TODO()
            Contact.LinkedIn -> TODO()
            Contact.Mail -> TODO()
        }
    }
}