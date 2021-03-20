package com.netchar.nicknamer.presentation.infrastructure

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.netchar.nicknamer.presentation.toWebUri
import timber.log.Timber

internal class ExternalAppServiceImpl(val context: Context) : ExternalAppService {

    override fun composeEmail(to: String, subject: String, message: String) {
        val uri = Uri.parse("mailto:")
        val emailIntent = Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to)) // recipients
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        val packageManager = context.packageManager
        val resolvingActivities = packageManager.queryIntentActivities(emailIntent, 0)
        val isIntentSafe: Boolean = resolvingActivities.isNotEmpty()

        if (isIntentSafe) {
            val intent: Intent = if (resolvingActivities.count() > 1) {
                Intent.createChooser(emailIntent, "Send email with:")
            } else {
                emailIntent
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Unable to find appropriate app for send an email.", Toast.LENGTH_LONG).show()
        }
    }

    override fun openUrlInExternalApp(app: ExternalAppService.ExternalApp, link: String) {
        openUrlInExternalApp(app.packageName, link)
    }

    override fun openUrlInExternalApp(packageName: String, link: String) {
        val packageManager = context.packageManager
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)

        if (launchIntent == null) {
            openWebPage(link)
        } else {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(launchIntent)
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
            val packageManager = context.packageManager

            if (webViewIntent.resolveActivity(packageManager) != null) {
                webViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(webViewIntent)
                return true
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
        return false
    }
}