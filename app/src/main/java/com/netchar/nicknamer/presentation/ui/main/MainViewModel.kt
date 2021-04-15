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

import android.app.Application
import androidx.lifecycle.*
import com.netchar.nicknamer.App
import com.netchar.nicknamer.R
import com.netchar.nicknamer.domen.NicknameGenerator.*
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsEvent
import com.netchar.nicknamer.presentation.infrastructure.copyToClipboard
import com.netchar.nicknamer.presentation.infrastructure.helpers.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
        application: Application,
        private val nicknameService: NicknameGeneratorService,
        private val analytics: Analytics
) : AndroidViewModel(application), DefaultLifecycleObserver {
    private var job: Job? = null
    private val mutableNickname = MutableLiveData<Nickname>()
    private val mutableMessage = SingleLiveEvent<Int>()

    val nickname: LiveData<Nickname> = mutableNickname
    val toastMessage: LiveData<Int> = mutableMessage

    val isNicknameFavorite: LiveData<Boolean> get() = mutableNickname.map { nickname ->
        nicknameService.isFavorite(nickname)
    }

    // Binding
    val nicknameLength = MutableLiveData(5.0f)
    val gender = MutableLiveData(Config.Gender.MALE)
    val alphabet = MutableLiveData(Config.Alphabet.LATIN)

    init {
        generateNewNickname()
    }

    override fun onStart(owner: LifecycleOwner) {
        analytics.trackScreen(AnalyticsEvent.ViewScreen(AnalyticsEvent.ViewScreen.Screen.MAIN))
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
        }
    }

    fun onFavoriteChecked(checked: Boolean) {
        val nickname = requireNotNull(mutableNickname.value)

        if (checked) {
            nicknameService.addToFavorites(nickname)
        } else {
            nicknameService.removeFromFavorites(nickname)
        }
    }

    fun copyToClipboard() {
        analytics.trackEvent(AnalyticsEvent.Event("copy_to_clipboard"))
        mutableMessage.value = R.string.message_copied_to_clipboard

        val nickname = requireNotNull(nickname.value).toString()
        getApplication<App>().copyToClipboard(nickname)
    }
}