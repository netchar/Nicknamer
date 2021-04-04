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

package com.netchar.nicknamer.presentation.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.netchar.nicknamer.BuildConfig
import com.netchar.nicknamer.R
import com.netchar.nicknamer.data.*
import com.netchar.nicknamer.data.database.NicknamesDatabase
import com.netchar.nicknamer.data.database.NicknamesDatabaseImpl
import com.netchar.nicknamer.data.mappers.NicknameMapper
import com.netchar.nicknamer.domen.NicknameDataSource
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.domen.service.NicknameGeneratorService.*
import com.netchar.nicknamer.domen.service.NicknameGeneratorServiceImpl
import com.netchar.nicknamer.presentation.infrastructure.*
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsImpl
import com.netchar.nicknamer.presentation.ui.about.AboutViewModel
import com.netchar.nicknamer.presentation.ui.favorites.FavoritesViewModel
import com.netchar.nicknamer.presentation.ui.main.MainFragment
import com.netchar.nicknamer.presentation.ui.main.MainViewModel
import com.netchar.nicknamer.presentation.infrastructure.helpers.ViewGroupSelectionMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

object Modules {
    private val appModule = module {
        single {
            if (BuildConfig.DEBUG) {
                DebugTree()
            } else {
                ReleaseTree()
            }
        }

        single { FirebaseAnalytics.getInstance(androidContext()) }
        single { FirebaseCrashlytics.getInstance() }
        single { androidApplication().getSharedPreferences("prefs", Context.MODE_PRIVATE) } bind SharedPreferences::class

        singleBy<BuildConfiguration, BuildConfigurationImpl>()
        singleBy<Thread.UncaughtExceptionHandler, AppUncaughtExceptionHandler>()
        singleBy<Analytics, AnalyticsImpl>()
    }

    private val serviceModule = module {
        singleBy<NicknameDataSource, NicknameDataSourceImpl>()
        singleBy<NicknameGeneratorService, NicknameGeneratorServiceImpl>()
        singleBy<LibrariesProvider, LibraryProviderImpl>()
        singleBy<ExternalAppService, ExternalAppServiceImpl>()
        singleBy<NicknamesDatabase, NicknamesDatabaseImpl>()

        single { NicknameMapper() }
    }

    private val viewModelModule = module {
        viewModel<MainViewModel>()
        viewModel<AboutViewModel>()
        viewModel<FavoritesViewModel>()
    }

    private val mainFragmentModule = module {
        scope<MainFragment> {
            scoped(named(Constants.MainFragment.GENDER_GROUP_MAPPER)) {
                ViewGroupSelectionMapper(
                        R.id.main_radio_btn_male to Config.Gender.MALE,
                        R.id.main_radio_btn_female to Config.Gender.FEMALE
                )
            }
            scoped(named(Constants.MainFragment.ALPHABET_GROUP_MAPPER)) {
                ViewGroupSelectionMapper(
                        R.id.main_radio_btn_cyrillic to Config.Alphabet.CYRILLIC,
                        R.id.main_radio_btn_latin to Config.Alphabet.LATIN,
                )
            }
        }
    }

    val ViewModels get() = listOf(viewModelModule)
    val Fragments get() = listOf(mainFragmentModule)
    val All get() = ViewModels + appModule + serviceModule + Fragments
}