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

