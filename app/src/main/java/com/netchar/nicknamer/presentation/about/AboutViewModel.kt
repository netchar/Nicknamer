package com.netchar.nicknamer.presentation.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.netchar.nicknamer.presentation.infrastructure.LibrariesProvider
import com.netchar.nicknamer.presentation.infrastructure.BuildConfiguration

class AboutViewModel(
    private val buildConfiguration: BuildConfiguration
) : ViewModel() {
    private val mutableContacts = MutableLiveData<List<Contact>>()

    val contacts: LiveData<List<Contact>> = mutableContacts

    init {
        mutableContacts.value = listOf(
            Contact.Instagram,
            Contact.LinkedIn,
            Contact.Mail,
        )
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