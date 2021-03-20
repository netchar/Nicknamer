package com.netchar.nicknamer.presentation.infrastructure

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.netchar.nicknamer.presentation.toWebUri
import timber.log.Timber

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

internal class ExternalAppServiceImpl(val context: Context) : ExternalAppService {
    private val appPackageManager get() = context.packageManager

    override fun composeEmail(to: String, subject: String, message: String) {
        val uri = Uri.parse("mailto:")
        val emailIntent = buildEmailIntent(uri, to, subject, message)
        val emailIntentResolver = appPackageManager.queryIntentActivities(emailIntent, 0)

        if (emailIntentResolver.isNotEmpty()) {
            val intent: Intent = getChooserIntent(emailIntentResolver, emailIntent)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            Toast.makeText(context, "Unable to find appropriate app for send an email.", Toast.LENGTH_LONG).show()
        }
    }

    private fun buildEmailIntent(uri: Uri?, to: String, subject: String, message: String) : Intent {
        return Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to)) // recipients
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
    }

    private fun getChooserIntent(resolvingActivities: List<ResolveInfo>, emailIntent: Intent): Intent {
        return if (resolvingActivities.count() > 1) {
            Intent.createChooser(emailIntent, "Send email with:")
        } else {
            emailIntent
        }
    }

    override fun openUrlInExternalApp(app: ExternalAppService.ExternalApp, link: String) {
        openUrlInExternalApp(app.packageName, link)
    }

    override fun openUrlInExternalApp(packageName: String, link: String) {
        val launchIntent = appPackageManager.getLaunchIntentForPackage(packageName)

        if (launchIntent == null) {
            openWebPage(link)
        } else {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(launchIntent)
        }
    }

    override fun openWebPage(url: String): Boolean {
        val uri = url.toWebUri()
        return openWebPage(uri)
    }

    override fun openWebPage(url: Uri): Boolean {
        // Try using Chrome Custom Tabs.
        if (tryOpenInChromeTabs(url)) {
            return true
        }

        // Fall back to launching a default web browser intent.
        if (tryOpenInSystemBrowser(url)) {
            return true
        }

        // Unable to show the web page.
        return false
    }

    private fun tryOpenInChromeTabs(uri: Uri): Boolean {
        try {
            val intent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build()
            intent.launchUrl(context, uri)
            return true
        } catch (ex: Exception) {
            Timber.e(ex)
        }
        return false
    }

    private fun tryOpenInSystemBrowser(uri: Uri): Boolean {
        try {
            val webViewIntent = Intent(Intent.ACTION_VIEW, uri)

            if (webViewIntent.resolveActivity(appPackageManager) != null) {
                webViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(webViewIntent)
                return true
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
        return false
    }

    private fun startActivity(launchIntent: Intent?) {
        context.startActivity(launchIntent)
    }
}