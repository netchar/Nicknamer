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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.service.NicknameGeneratorService
import kotlinx.coroutines.launch

class FavoritesViewModel(
        private val nicknameGeneratorService: NicknameGeneratorService
) : ViewModel() {

    private val mutableFavoriteNicknames = MutableLiveData<List<Nickname>>()

    val nicknames: LiveData<List<Nickname>> = mutableFavoriteNicknames

    init {
        update()
    }

    fun update() {
        mutableFavoriteNicknames.value = nicknameGeneratorService.getFavoriteNicknames()
    }

    fun removeFromFavorites(nickname: Nickname) {
        nicknameGeneratorService.removeFromFavorites(nickname)
    }
}