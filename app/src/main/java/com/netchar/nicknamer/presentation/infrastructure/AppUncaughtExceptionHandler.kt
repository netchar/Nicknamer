/*
 * Copyright © 2021 Eugene Glushankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netchar.nicknamer.presentation.infrastructure

import android.content.Context
import android.os.Build
import androidx.core.os.ConfigurationCompat
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AppUncaughtExceptionHandler(
        context: Context,
        private val buildConfig: BuildConfiguration
) : Thread.UncaughtExceptionHandler {
    private val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", ConfigurationCompat.getLocales(context.resources.configuration).get(0))
    private val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
    private val runtime by lazy { Runtime.getRuntime() }

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        val dumpDate = Date(System.currentTimeMillis())

        buildString {
            getFullStack(exception, this)

            appendLine()
            appendLine("************ Timestamp  ${formatter.format(dumpDate)} ************")
            appendLine()
            appendLine("************ DEVICE INFORMATION ***********")
            appendLine("Brand: " + Build.BRAND)
            appendLine("Device: " + Build.DEVICE)
            appendLine("Model: " + Build.MODEL)
            appendLine("Id: " + Build.ID)
            appendLine("Product: " + Build.PRODUCT)
            appendLine()
            appendLine("************ BUILD INFO ************")
            appendLine("SDK: " + Build.VERSION.SDK_INT)
            appendLine("Release: " + Build.VERSION.RELEASE)
            appendLine("Incremental: " + Build.VERSION.INCREMENTAL)
            appendLine("Version Name: " + buildConfig.getVersionName())
            appendLine("Version Code: " + buildConfig.getVersionCode())

            Timber.e(this.toString())
        }

        defaultExceptionHandler?.uncaughtException(thread, exception)
    }

    private fun getFullStack(exception: Throwable?, builder: StringBuilder) {

        if (exception == null)
            return

        builder.apply {
            appendLine()
            appendLine("Exception: ${exception.javaClass.name}")
            appendLine("Message: ${exception.message}")
            appendLine("Stacktrace:")
            for (element in exception.stackTrace) {
                append("\t\t").append(element.toString()).append("\n")
            }
        }

        getFullStack(exception.cause, builder)
    }
}