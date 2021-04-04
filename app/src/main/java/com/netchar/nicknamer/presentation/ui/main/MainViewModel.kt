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
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.domen.service.NicknameGeneratorService.Config
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
        private val nicknameService: NicknameGeneratorService,
        private val analytics: Analytics
) : ViewModel() {
    private val mutableNickname = MutableLiveData<Nickname>()
    private val mutableGender = MutableLiveData(Config.Gender.MALE)
    private val mutableAlphabet = MutableLiveData(Config.Alphabet.LATIN)
    private val mutableNicknameLength = MutableLiveData(5.0f)
    private val mutableFavorite = MutableLiveData<Boolean>()

    val gender: LiveData<Config.Gender> = mutableGender
    val alphabet: LiveData<Config.Alphabet> = mutableAlphabet
    val nicknameLength: LiveData<Float> = mutableNicknameLength
    val isFavorite: LiveData<Boolean> = mutableFavorite

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
            val config = Config(
                    requireNotNull(mutableNicknameLength.value).toInt(),
                    requireNotNull(mutableGender.value),
                    requireNotNull(mutableAlphabet.value)
            )
            analytics.trackEvent(AnalyticsEvent.GenerateNickname(config))
            mutableNickname.value = nicknameService.generateNickname(config)
            updateFavoriteState()
        }
    }

    fun setGender(gender: Config.Gender) {
        mutableGender.value = gender
    }

    fun setAlphabet(alphabet: Config.Alphabet) {
        mutableAlphabet.value = alphabet
    }

    fun setLength(length: Float) {
        mutableNicknameLength.value = length
    }

    fun addToFavorites(nickname: String) {
        nicknameService.addToFavorites(Nickname(nickname))
    }

    fun removeFromFavorites(nickname: String) {
        nicknameService.removeFromFavorites(Nickname(nickname))
    }

    fun updateFavoriteState() {
        mutableFavorite.value = nicknameService.isFavorite(requireNotNull(mutableNickname.value))
    }
}