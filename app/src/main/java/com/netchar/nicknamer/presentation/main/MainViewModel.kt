package com.netchar.nicknamer.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netchar.nicknamer.domen.models.Config
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import kotlinx.coroutines.launch

class MainViewModel(
        private val nicknameService: NicknameGeneratorService
) : ViewModel() {
    private val mutableNickname = MutableLiveData<Nickname>()

    val nickname: LiveData<Nickname> = mutableNickname

    fun generateNewNickname(config: Config) {
        viewModelScope.launch {
            mutableNickname.value = nicknameService.generateNickname(config)
        }
    }
}