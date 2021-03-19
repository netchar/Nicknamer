package com.netchar.nicknamer

import android.app.Application
import com.netchar.nicknamer.presentation.di.Modules
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, Modules.Application + Modules.ViewModels + Modules.Services)

        Timber.plant(get())
        Thread.setDefaultUncaughtExceptionHandler(get())
    }
}

