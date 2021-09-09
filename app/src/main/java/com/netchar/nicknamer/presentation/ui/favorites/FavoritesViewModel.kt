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

package com.netchar.nicknamer.presentation.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.netchar.nicknamer.App
import com.netchar.nicknamer.R
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.service.FavoritesService
import com.netchar.nicknamer.presentation.infrastructure.analytics.Analytics
import com.netchar.nicknamer.presentation.infrastructure.analytics.AnalyticsEvent
import com.netchar.nicknamer.presentation.infrastructure.copyToClipboard
import com.netchar.nicknamer.presentation.infrastructure.helpers.SingleLiveEvent

class FavoritesViewModel(
        application: Application,
        private val nicknameGeneratorFacade: FavoritesService,
        private val analytics: Analytics
) : AndroidViewModel(application) {
    private val mutableMessage = SingleLiveEvent<Int>()
    private val mutableFavoriteNicknames = MutableLiveData<List<Nickname>>()
    private val unmodifiedFavorites = nicknameGeneratorFacade.getFavoriteNicknames()

    val nicknames: LiveData<List<Nickname>> = mutableFavoriteNicknames
    val toastMessage: LiveData<Int> = mutableMessage

    init {
        restoreFavorites()
    }

    fun removeFromFavorites(nickname: Nickname) {
        mutableFavoriteNicknames.value = currentFavorites - nickname
    }

    private val currentFavorites: List<Nickname> get() = mutableFavoriteNicknames.value.orEmpty()

    fun restoreFavorites() {
        mutableFavoriteNicknames.value = unmodifiedFavorites
    }

    fun applyRemoving() {
        unmodifiedFavorites
            .subtract(currentFavorites)
            .forEach { nicknameToDelete ->
                nicknameGeneratorFacade.removeFromFavorites(nicknameToDelete)
            }
    }

    fun copyToClipboard(nickname: Nickname) {
        analytics.trackEvent(AnalyticsEvent.CopyToClipboard(nickname.toString()))

        mutableMessage.value = R.string.message_copied_to_clipboard
        getApplication<App>().copyToClipboard(nickname.toString())
    }
}