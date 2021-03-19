package com.netchar.nicknamer.presentation.di

import android.content.Context
import com.netchar.common.services.LibrariesProvider
import com.netchar.nicknamer.data.NicknameModelsDataSourceImpl
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.domen.service.NicknameGeneratorServiceImpl
import com.netchar.nicknamer.domen.NicknameModelsDataSource
import com.netchar.nicknamer.presentation.infrastructure.AppLibrariesProvider
import com.netchar.nicknamer.presentation.infrastructure.BuildConfiguration
import com.netchar.nicknamer.presentation.infrastructure.BuildConfigurationImpl
import com.netchar.nicknamer.presentation.infrastructure.UncaughtExceptionHandler

class DependenciesProvider {

    fun provideDataSource(context: Context): NicknameModelsDataSource {
        return NicknameModelsDataSourceImpl(context)
    }

    fun provideNicknameGeneratorService(source: NicknameModelsDataSource): NicknameGeneratorService {
        return NicknameGeneratorServiceImpl(source)
    }

    fun provideBuildConfiguration(context: Context) : BuildConfiguration {
        return BuildConfigurationImpl(context)
    }

    fun provideUncaughtExceptionHandler(context: Context, buildConfiguration: BuildConfiguration) : UncaughtExceptionHandler {
        return UncaughtExceptionHandler.create(context, buildConfiguration)
    }

    fun provideThirdPartyLibrariesProvider() : LibrariesProvider {
        return AppLibrariesProvider()
    }
}