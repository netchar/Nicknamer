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

import android.content.SharedPreferences
import com.netchar.nicknamer.domen.models.Nickname

class NicknamesDatabaseImpl(
        private val sharedPreferences: SharedPreferences
) : NicknamesDatabase {
    private companion object {
        const val KEY_NICKNAMES = "key_nicknames"
    }

    override fun getAll(): List<Nickname> {
        if (sharedPreferences.contains(KEY_NICKNAMES)) {
            return sharedPreferences.getStringSet(KEY_NICKNAMES, emptySet())?.map { Nickname(it) }.orEmpty()
        }

        return listOf()
    }

    override fun add(nickname: Nickname) {
        val existList = getAll()
            .toMutableSet()
            .apply { this.add(nickname) }
            .map { it.value }
            .toSet()

        sharedPreferences.edit().putStringSet(KEY_NICKNAMES, existList).apply()
    }

    override fun remove(nickname: Nickname) {
        val existingList = getAll()
            .toMutableSet()
            .apply { this.remove(nickname) }
            .map { it.value }
            .toSet()

        sharedPreferences.edit().putStringSet(KEY_NICKNAMES, existingList).apply()
    }
}