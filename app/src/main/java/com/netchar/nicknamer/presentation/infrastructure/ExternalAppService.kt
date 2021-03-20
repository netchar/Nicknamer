package com.netchar.nicknamer.presentation.infrastructure

import android.net.Uri

interface ExternalAppService {
    enum class ExternalApp(val appName: String, val packageName: String) {
        INSTAGRAM("Instagram", "com.instagram.android"),
        LINKED_IN("LinkedIn", "com.linkedin.android")
    }

    fun openUrlInExternalApp(app: ExternalApp, link: String)

    fun openUrlInExternalApp(packageName: String, link: String)

    fun openWebPage(url: String): Boolean

    fun openWebPage(url: Uri): Boolean

    fun composeEmail(to: String, subject: String, message: String = "")
}