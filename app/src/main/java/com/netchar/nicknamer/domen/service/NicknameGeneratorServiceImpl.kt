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

import com.netchar.nicknamer.data.NicknamesDatabase
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.NicknameModelsDataSource
import com.netchar.nicknamer.domen.models.Nickname.Companion.orEmpty

class NicknameGeneratorServiceImpl(
        private val dataSource: NicknameModelsDataSource,
        private val database: NicknamesDatabase
) : NicknameGeneratorService {
    companion object {
        private const val PEOPLE_PREFIX = "people"
    }

    override suspend fun generateNickname(config: NicknameGeneratorService.Config): Nickname {
        val key = "${PEOPLE_PREFIX}_${config.gender.value}_${config.alphabet.value}"
        val source = dataSource.getDataSource()
        return source[key]?.generateNickname(config.nicknameLength).orEmpty()
    }

    override fun addToFavorites(nickname: Nickname) {
        database.add(nickname)
    }

    override fun removeFromFavorites(nickname: Nickname) {
        database.remove(nickname)
    }

    override fun getFavoriteNicknames(): List<Nickname> {
        return database.getAll()
    }
}