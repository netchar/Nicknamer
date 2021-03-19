package com.netchar.nicknamer

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.presentation.di.DependenciesProvider
import com.netchar.nicknamer.presentation.infrastructure.DebugTree
import com.netchar.nicknamer.presentation.infrastructure.ReleaseTree
import com.netchar.nicknamer.presentation.infrastructure.UncaughtExceptionHandler
import timber.log.Timber

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    val provider = DependenciesProvider()

    val nicknameService by lazy {
        val source = provider.provideDataSource(this)
        provider.provideNicknameGeneratorService(source)
    }

    val buildConfiguration by lazy {
        provider.provideBuildConfiguration(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        Thread.setDefaultUncaughtExceptionHandler(provider.provideUncaughtExceptionHandler(this, buildConfiguration))
    }
}

