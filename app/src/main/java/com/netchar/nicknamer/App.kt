package com.netchar.nicknamer

import android.app.Application
import com.netchar.nicknamer.domen.NicknameGenerator
import com.netchar.nicknamer.presentation.di.DependenciesProvider

class App : Application() {
    companion object {

    }

    val nicknameGenerator: NicknameGenerator by lazy {
        val provider = DependenciesProvider()
        provider.provideNicknameGenerator()
    }

    override fun onCreate() {
        super.onCreate()
    }
}

