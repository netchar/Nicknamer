package com.netchar.nicknamer.presentation.di

import android.content.Context
import com.netchar.nicknamer.data.NicknameModelsDataSourceImpl
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.domen.service.NicknameGeneratorServiceImpl
import com.netchar.nicknamer.domen.NicknameModelsDataSource

class DependenciesProvider {

    fun provideDataSource(context: Context): NicknameModelsDataSource {
        return NicknameModelsDataSourceImpl(context)
    }

    fun provideNicknameGeneratorService(source: NicknameModelsDataSource): NicknameGeneratorService {
        return NicknameGeneratorServiceImpl(source)
    }
}