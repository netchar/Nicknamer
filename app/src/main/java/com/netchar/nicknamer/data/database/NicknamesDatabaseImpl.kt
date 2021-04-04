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

package com.netchar.nicknamer.data.database

import android.content.SharedPreferences
import com.netchar.nicknamer.data.database.dto.DBNickname

class NicknamesDatabaseImpl(
        private val sharedPreferences: SharedPreferences
) : NicknamesDatabase {
    private companion object {
        const val KEY_NICKNAMES = "key_nicknames"
    }

    override fun getAll(): List<DBNickname> {
        if (sharedPreferences.contains(KEY_NICKNAMES)) {
            return getCachedNicknames().map { DBNickname.parse(it) }
        }

        return listOf()
    }

    private fun getCachedNicknames(): MutableSet<String> {
        return sharedPreferences.getStringSet(KEY_NICKNAMES, emptySet()) ?: mutableSetOf()
    }

    override fun add(nickname: DBNickname) {
        val updatedSet = getCachedNicknames() + nickname.asPreferenceRecord()

        sharedPreferences.edit()
            .putStringSet(KEY_NICKNAMES, updatedSet)
            .apply()
    }

    override fun remove(nickname: DBNickname) {
        val updatedSet = getCachedNicknames() - nickname.asPreferenceRecord()

        sharedPreferences.edit()
            .putStringSet(KEY_NICKNAMES, updatedSet)
            .apply()
    }

    override fun getByName(name: String): DBNickname? {
        val record = getCachedNicknames().find { it.contains(name) }
        return record?.let { DBNickname.parse(it) }
    }

    private fun DBNickname.asPreferenceRecord(): String = "${value}_${timestamp}"
}