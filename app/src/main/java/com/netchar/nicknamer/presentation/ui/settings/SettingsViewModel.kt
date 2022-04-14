/*
 * Copyright (c) 2022 Eugene Glushankov
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

package com.netchar.nicknamer.presentation.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.netchar.nicknamer.App
import com.netchar.nicknamer.presentation.infrastructure.helpers.SingleLiveEvent
import com.netchar.nicknamer.presentation.infrastructure.settings.AppSettings
import timber.log.Timber
import java.util.*

class SettingsViewModel(
    private val appSettings: AppSettings,
    application: Application
) : AndroidViewModel(application) {
    private var mutableNewLanguageSet = SingleLiveEvent<Unit>()

    val newLanguageSet: LiveData<Unit> = mutableNewLanguageSet

    fun applyNewLanguage(newLanguage: Any) = runCatching {
        val newLocale = Locale(newLanguage.toString())
        val application = getApplication<App>()
        appSettings.localization.changeLocale(application.applicationContext, newLocale)
        mutableNewLanguageSet.call()
    }.onFailure {
        Timber.e(it)
    }

    fun applyDayNight(newValue: Any) = runCatching {
        val mode = Integer.parseInt(newValue.toString())
        appSettings.theme.applyDayNightMode(mode)
    }.onFailure {
        Timber.e(it)
    }

    fun getCurrentLanguage() : String {
        return appSettings.localization.getLanguage()
    }

    fun getCurrentTheme(): Int {
        return appSettings.theme.getSavedThemeMode()
    }
}