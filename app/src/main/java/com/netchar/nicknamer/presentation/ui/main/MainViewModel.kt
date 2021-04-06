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

import androidx.databinding.Bindable
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
    private var job: Job? = null
    private val mutableNickname = MutableLiveData<Nickname>()
    private val mutableFavorite = MutableLiveData<Boolean>()

    val isFavorite: LiveData<Boolean> get() = mutableFavorite
    val nickname: LiveData<Nickname> get() = mutableNickname

    // Binding
    val nicknameLength = MutableLiveData(5.0f)
    val gender = MutableLiveData(Config.Gender.MALE)
    val alphabet = MutableLiveData(Config.Alphabet.LATIN)

    init {
        generateNewNickname()
    }

    fun generateNewNickname() {
        if (job?.isActive == true) {
            return
        }

        job = viewModelScope.launch {
            val config = Config(
                    requireNotNull(nicknameLength.value).toInt(),
                    requireNotNull(gender.value),
                    requireNotNull(alphabet.value)
            )
            analytics.trackEvent(AnalyticsEvent.GenerateNickname(config))
            mutableNickname.value = nicknameService.generateNickname(config)
            updateFavoriteState()
        }
    }

    fun onFavoriteChecked(checked: Boolean, nickname: String) {
        if (checked) {
            nicknameService.addToFavorites(Nickname(nickname))
        } else {
            nicknameService.removeFromFavorites(Nickname(nickname))
        }
    }

    fun updateFavoriteState() {
        mutableFavorite.value = nicknameService.isFavorite(requireNotNull(mutableNickname.value))
    }
}