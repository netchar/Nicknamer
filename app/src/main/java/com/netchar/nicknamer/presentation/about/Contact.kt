package com.netchar.nicknamer.presentation.about

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.netchar.nicknamer.R

sealed class Contact(@DrawableRes val image: Int, @StringRes val description: Int) {
    object Instagram : Contact(R.drawable.ic_instagram, R.string.contact_follow_on_instagram)
    object LinkedIn : Contact(R.drawable.ic_linkedin, R.string.contact_connect_on_linked_id)
    object Mail : Contact(R.drawable.ic_gmail, R.string.contact_send_email)
}