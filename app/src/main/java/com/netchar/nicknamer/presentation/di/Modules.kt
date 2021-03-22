/*
 * Copyright  (c) 2021 Eugene Glushankov
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

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.netchar.nicknamer.BuildConfig
import com.netchar.nicknamer.data.NicknameModelsDataSourceImpl
import com.netchar.nicknamer.domen.NicknameModelsDataSource
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.domen.service.NicknameGeneratorServiceImpl
import com.netchar.nicknamer.presentation.ui.about.AboutViewModel
import com.netchar.nicknamer.presentation.infrastructure.*
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsImpl
import com.netchar.nicknamer.presentation.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
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
        singleBy<BuildConfiguration, BuildConfigurationImpl>()
        singleBy<Thread.UncaughtExceptionHandler, AppUncaughtExceptionHandler>()
        singleBy<Analytics, AnalyticsImpl>()
    }

    private val serviceModule = module {
        singleBy<NicknameModelsDataSource, NicknameModelsDataSourceImpl>()
        singleBy<NicknameGeneratorService, NicknameGeneratorServiceImpl>()
        singleBy<LibrariesProvider, LibraryProviderImpl>()
        singleBy<ExternalAppService, ExternalAppServiceImpl>()
    }

    private val viewModelModule = module {
        viewModel<MainViewModel>()
        viewModel<AboutViewModel>()
    }

    val ViewModels get() = listOf(viewModelModule)
    val Application get() = listOf(appModule)
    val Services get() = listOf(serviceModule)
}

