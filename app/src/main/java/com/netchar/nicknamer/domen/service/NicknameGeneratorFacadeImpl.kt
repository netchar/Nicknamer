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

package com.netchar.nicknamer.domen.service

import com.netchar.nicknamer.domen.NicknameGenerator
import com.netchar.nicknamer.domen.NicknameRepository
import com.netchar.nicknamer.domen.models.Nickname

class NicknameGeneratorFacadeImpl(
    private val generator: NicknameGenerator,
    private val repository: NicknameRepository
) : NicknameGeneratorFacade {
    override suspend fun generateNickname(config: NicknameGenerator.Config): Nickname {
        return generator.generateNickname(config)
    }

    override fun addToFavorites(nickname: Nickname) {
        repository.addToFavorites(nickname)
    }

    override fun removeFromFavorites(nickname: Nickname) {
        repository.removeFromFavorites(nickname)
    }

    override fun getFavoriteNicknames(): List<Nickname> {
        return repository.getFavoriteNicknames()
    }

    override fun isFavorite(nickname: Nickname): Boolean {
        return repository.isFavorite(nickname)
    }

    override fun getHistory(): List<Nickname> {
        return repository.getHistory()
    }

    override fun addToHistory(nickname: Nickname) {
        repository.addToHistory(nickname)
    }
}

