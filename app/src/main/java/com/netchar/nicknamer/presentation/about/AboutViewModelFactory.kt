package com.netchar.nicknamer.presentation.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.netchar.common.services.LibrariesProvider
import com.netchar.nicknamer.App
import com.netchar.nicknamer.presentation.infrastructure.BuildConfiguration

class AboutViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LibrariesProvider::class.java, BuildConfiguration::class.java)
            .newInstance(App.instance.provider.provideThirdPartyLibrariesProvider(), App.instance.buildConfiguration)
    }
}