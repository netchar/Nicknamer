package com.netchar.nicknamer.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.netchar.nicknamer.R


fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(this.getString(R.string.copy_clipboard_label), text)
    clipboard.setPrimaryClip(clip)
}