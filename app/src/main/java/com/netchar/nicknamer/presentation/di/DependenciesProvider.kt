package com.netchar.nicknamer.presentation.di

import com.netchar.nicknamer.data.NicknameGeneratorImpl
import com.netchar.nicknamer.domen.NicknameGenerator

class DependenciesProvider {

    fun provideNicknameGenerator() : NicknameGenerator {
        return NicknameGeneratorImpl()
    }
}