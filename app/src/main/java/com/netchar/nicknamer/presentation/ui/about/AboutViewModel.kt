package com.netchar.nicknamer.presentation.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.netchar.nicknamer.presentation.infrastructure.*
import com.netchar.nicknamer.presentation.infrastructure.ExternalAppService.*

class AboutViewModel(
    private val buildConfiguration: BuildConfiguration,
    private val externalAppService: ExternalAppService
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
            Contact.Instagram -> externalAppService.openUrlInExternalApp(ExternalApp.INSTAGRAM, URL_DEVELOPER_INSTAGRAM)
            Contact.LinkedIn -> externalAppService.openUrlInExternalApp(ExternalApp.LINKED_IN, URL_DEVELOPER_LINKEDIN)
            Contact.Mail -> externalAppService.composeEmail(MAIL_ADDRESS_AUTHOR, "[Nicknamer] Hi, Eugene!")
        }
    }
}