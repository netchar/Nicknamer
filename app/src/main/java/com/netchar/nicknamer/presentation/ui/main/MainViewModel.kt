package com.netchar.nicknamer.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netchar.nicknamer.R
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.domen.service.NicknameGeneratorService.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
        private val nicknameService: NicknameGeneratorService
) : ViewModel() {
    private val mutableNickname = MutableLiveData<Nickname>()
    private var gender: Config.Gender = Config.Gender.MALE
    private var alphabet: Config.Alphabet = Config.Alphabet.LATIN
    private var nicknameLength: Float = 5.0f
    private var job: Job? = null

    val nickname: LiveData<Nickname> = mutableNickname

    init {
        generateNewNickname()
    }

    fun generateNewNickname() {
        if (job?.isActive == true) {
            return
        }

        job = viewModelScope.launch {
            val generateNickname = nicknameService.generateNickname(Config(nicknameLength.toInt(), gender, alphabet))
            mutableNickname.value = generateNickname
        }
    }

    fun setGender(checkedId: Int) {
        gender = when (checkedId) {
            R.id.main_radio_btn_male -> Config.Gender.MALE
            else -> Config.Gender.FEMALE
        }
    }

    fun setAlphabet(checkedId: Int) {
        alphabet = when (checkedId) {
            R.id.main_radio_btn_latin -> Config.Alphabet.LATIN
            else -> Config.Alphabet.CYRILLIC
        }
    }

    fun setLength(length: Float) {
        nicknameLength = length
    }
}