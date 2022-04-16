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

package com.netchar.nicknamer.presentation.infrastructure

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate.*
import com.netchar.nicknamer.R

class ThemeHelper(
        private val application: Application,
        private val preferences: SharedPreferences
) {

    fun isDayThemeEnabled(context: Context): Boolean {
        val uiMode: Int = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return uiMode == Configuration.UI_MODE_NIGHT_NO
    }

    fun applyDayNightMode(@NightMode mode: Int) {
        setDefaultNightMode(mode)
    }

    @NightMode
    fun getSavedThemeMode(): Int {
        val themeKey = application.getString(R.string.preference_option_key_theme)
        val theme = preferences.getString(themeKey, MODE_NIGHT_NO.toString())
        return requireNotNull(theme).toInt()
    }
}