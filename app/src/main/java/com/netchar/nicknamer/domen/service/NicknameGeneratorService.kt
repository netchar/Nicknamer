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

import com.netchar.nicknamer.domen.models.Nickname

interface NicknameGeneratorService {
    /**
     * Generates random nickname based on [config]
     */
    suspend fun generateNickname(config: Config) : Nickname

    fun addToFavorites(nickname: Nickname)

    fun removeFromFavorites(nickname: Nickname)

    fun getFavoriteNicknames(): List<Nickname>

    data class Config(
        val nicknameLength: Int = 5,
        val gender: Gender = Gender.MALE,
        val alphabet: Alphabet = Alphabet.LATIN
    ) {
        enum class Gender(val value: String) { MALE("male"), FEMALE("female") }
        enum class Alphabet(val value: String) { LATIN("latin"), CYRILLIC("cyrillic") }
    }
}