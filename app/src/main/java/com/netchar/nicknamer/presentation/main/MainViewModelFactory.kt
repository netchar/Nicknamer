package com.netchar.nicknamer.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.netchar.nicknamer.App
import com.netchar.nicknamer.domen.service.NicknameGeneratorService

class MainViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val nicknameService = App.instance.nicknameService
        return modelClass.getConstructor(NicknameGeneratorService::class.java).newInstance(nicknameService)
    }
}