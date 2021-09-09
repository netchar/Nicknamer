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
import com.netchar.nicknamer.domen.service.FavoritesService
import com.netchar.nicknamer.domen.service.NicknameGeneratorFacade
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsEvent
import com.netchar.nicknamer.presentation.infrastructure.copyToClipboard
import com.netchar.nicknamer.presentation.infrastructure.helpers.SingleLiveEvent
import com.netchar.nicknamer.presentation.infrastructure.isActive
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
        application: Application,
        private val nicknameService: NicknameGeneratorFacade,
        private val analytics: Analytics
) : AndroidViewModel(application), DefaultLifecycleObserver {
    private var job: Job? = null
    private val mutableNicknameItem = MutableLiveData<NicknameItem>()
    private val mutableToastMessage = SingleLiveEvent<Int>()

    val history: List<NicknameItem> get() = nicknameService.getHistory().map { NicknameItem(it, nicknameService) }
    val nicknameItem: LiveData<NicknameItem> = mutableNicknameItem
    val toastMessage: LiveData<Int> = mutableToastMessage

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
        if (job.isActive) {
            return
        }

        job = viewModelScope.launch {
            val config = composeConfig()
            mutableNicknameItem.value = generateNicknameItem(config).also(::putInHistory)
        }
    }

    private fun composeConfig(): Config {
        val nicknameLength = requireNotNull(nicknameLength.value).toInt()
        val gender = requireNotNull(gender.value)
        val alphabet = requireNotNull(alphabet.value)
        return Config(nicknameLength, gender, alphabet)
    }

    private suspend fun generateNicknameItem(config: Config): NicknameItem {
        analytics.trackEvent(AnalyticsEvent.GenerateNickname(config))

        val nickname = nicknameService.generateNickname(config)
        return NicknameItem(nickname, nicknameService)
    }

    private fun putInHistory(item: NicknameItem) {
        nicknameService.addToHistory(item.nickname)
    }

    fun copyToClipboard(nickname: String) {
        analytics.trackEvent(AnalyticsEvent.CopyToClipboard(nickname))
        mutableToastMessage.value = R.string.message_copied_to_clipboard

        getApplication<App>().copyToClipboard(nickname)
    }

    fun invalidateCurrentNicknameState() {
        mutableNicknameItem.value = mutableNicknameItem.value
    }

    data class NicknameItem(val nickname: Nickname, private val service: FavoritesService) {
        var isFavorite: Boolean
            get() = service.isFavorite(nickname)
            set(value) {
                if (value) {
                    service.addToFavorites(nickname)
                } else {
                    service.removeFromFavorites(nickname)
                }
            }

        override fun toString(): String {
            return nickname.toString()
        }
    }
}