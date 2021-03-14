package com.netchar.nicknamer

import android.app.Application
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.presentation.di.DependenciesProvider

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    private val provider = DependenciesProvider()

    lateinit var nicknameService: NicknameGeneratorService

    override fun onCreate() {
        super.onCreate()
        instance = this
        val source = provider.provideDataSource(this)
        val generator = provider.provideNicknameGeneratorService(source)
        nicknameService = generator
    }
}

