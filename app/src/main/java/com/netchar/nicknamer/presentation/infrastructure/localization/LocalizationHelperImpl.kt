/*
 * Copyright (c) 2021 Eugene Glushankov
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

package com.netchar.nicknamer.presentation.infrastructure.localization

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.core.os.ConfigurationCompat
import com.yariksoffice.lingver.Lingver
import java.util.*

class LocalizationHelperImpl : LocalizationHelper {
    private lateinit var lingver: Lingver

    override fun init(application: Application) {
        lingver = Lingver.init(application)
    }

    override fun changeLocale(context: Context, newLocale: Locale) {
        lingver.setLocale(context, newLocale)

        if (context is Activity) {
            context.recreate()
        }
    }

    override fun getLanguage(): String {
        return lingver.getLanguage()
    }
}