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
import com.netchar.nicknamer.data.NicknameModelsProviderImpl
import com.netchar.nicknamer.data.NicknameRepositoryImpl
import com.netchar.nicknamer.data.database.NicknamesDatabase
import com.netchar.nicknamer.data.database.NicknamesDatabaseImpl
import com.netchar.nicknamer.data.mappers.NicknameMapper
import com.netchar.nicknamer.domen.NicknameModelsProvider
import com.netchar.nicknamer.domen.NicknameRepository
import com.netchar.nicknamer.domen.service.NicknameGeneratorFacade
import com.netchar.nicknamer.domen.service.NicknameGeneratorFacadeImpl
import com.netchar.nicknamer.presentation.infrastructure.*
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsImpl
import com.netchar.nicknamer.presentation.ui.about.AboutViewModel
import com.netchar.nicknamer.presentation.ui.favorites.FavoritesViewModel
import com.netchar.nicknamer.presentation.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import timber.log.Timber
import java.lang.Thread.*

object Modules {
    private val appModule = module {
        fun provideTimber(): Timber.Tree {
            return if (BuildConfig.DEBUG) {
                DebugTree()
            } else {
                ReleaseTree()
            }
        }

        single { provideTimber() }
        single { FirebaseAnalytics.getInstance(androidContext()) }
        single { FirebaseCrashlytics.getInstance() }

        single<SharedPreferences> { androidApplication().getSharedPreferences("prefs", Context.MODE_PRIVATE) }
        single<BuildConfiguration> { BuildConfigurationImpl(get()) }
        single<UncaughtExceptionHandler> { AppUncaughtExceptionHandler(get(), get(), get()) }
        single<Analytics> { AnalyticsImpl(get()) }
    }


    private val serviceModule = module {
        single<NicknameRepository> { NicknameRepositoryImpl(get(), get(), get()) }
        single<NicknameGeneratorFacade> { NicknameGeneratorFacadeImpl(get()) }
        single<LibrariesProvider> { LibraryProviderImpl() }
        single<ExternalAppService> { ExternalAppServiceImpl(get()) }
        single<NicknamesDatabase> { NicknamesDatabaseImpl(get()) }
        single<NicknameModelsProvider> { NicknameModelsProviderImpl(get()) }

        single { NicknameMapper() }
    }

    private val viewModelModule = module {
        viewModel { MainViewModel(get(), get(), get()) }
        viewModel { AboutViewModel(get(), get()) }
        viewModel { FavoritesViewModel(get(), get(), get()) }
    }

    val All get() = viewModelModule + appModule + serviceModule
}