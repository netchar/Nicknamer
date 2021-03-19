package com.netchar.nicknamer.presentation.di

import com.netchar.nicknamer.presentation.infrastructure.LibrariesProvider
import com.netchar.nicknamer.BuildConfig
import com.netchar.nicknamer.data.NicknameModelsDataSourceImpl
import com.netchar.nicknamer.domen.NicknameModelsDataSource
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.domen.service.NicknameGeneratorServiceImpl
import com.netchar.nicknamer.presentation.about.AboutViewModel
import com.netchar.nicknamer.presentation.infrastructure.*
import com.netchar.nicknamer.presentation.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
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

        singleBy<BuildConfiguration, BuildConfigurationImpl>()
        singleBy<Thread.UncaughtExceptionHandler, AppUncaughtExceptionHandler>()
    }

    private val serviceModule = module {
        singleBy<NicknameModelsDataSource, NicknameModelsDataSourceImpl>()
        singleBy<NicknameGeneratorService, NicknameGeneratorServiceImpl>()
        singleBy<LibrariesProvider, LibraryProviderImpl>()
    }

    private val viewModelModule = module {
        viewModel<MainViewModel>()
        viewModel<AboutViewModel>()
    }

    val ViewModels get() = listOf(viewModelModule)
    val Application get() = listOf(appModule)
    val Services get() = listOf(serviceModule)
}

