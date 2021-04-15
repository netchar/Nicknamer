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

package com.netchar.nicknamer.data

import com.netchar.nicknamer.data.database.NicknamesDatabase
import com.netchar.nicknamer.data.mappers.NicknameMapper
import com.netchar.nicknamer.domen.NicknameRepository
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.models.NicknameModel
import com.netchar.nicknamer.domen.NicknameModelsProvider


class NicknameRepositoryImpl(
    private val modelsDataSource: NicknameModelsProvider,
    private val database: NicknamesDatabase,
    private val mapper: NicknameMapper
) : NicknameRepository {

    override fun getModels(): Map<String, NicknameModel> {
        return modelsDataSource.getModels()
    }

    override fun addToFavorites(nickname: Nickname) {
        val record = database.getByName(nickname.value)

        if (record == null) {
            database.add(mapper.mapToDb(nickname))
        }
    }

    override fun removeFromFavorites(nickname: Nickname) {
        val record = database.getByName(nickname.value)

        if (record != null) {
            database.remove(record)
        }
    }

    override fun getFavoriteNicknames(): List<Nickname> {
        return database.getAll()
            .sortedBy { it.timestamp }
            .map { mapper.mapToEntity(it) }
    }

    override fun isFavorite(nickname: Nickname): Boolean {
        val record = database.getByName(nickname.value)
        return record != null
    }
}