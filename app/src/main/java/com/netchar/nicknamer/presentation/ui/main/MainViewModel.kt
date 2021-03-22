/*
 * Copyright (c) 2021 Eugene Glushankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netchar.nicknamer.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netchar.nicknamer.R
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.domen.service.NicknameGeneratorService.*
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
        private val nicknameService: NicknameGeneratorService,
        private val analytics: Analytics
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
            val config = Config(nicknameLength.toInt(), gender, alphabet)
            analytics.trackEvent(AnalyticsEvent.GenerateNickname(config))
            mutableNickname.value = nicknameService.generateNickname(config)
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