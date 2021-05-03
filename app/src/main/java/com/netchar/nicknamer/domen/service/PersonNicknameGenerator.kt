package com.netchar.nicknamer.domen.service

import com.netchar.nicknamer.domen.NicknameGenerator
import com.netchar.nicknamer.domen.PersonNicknameModelsProvider
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.models.Nickname.Companion.orEmpty

class PersonNicknameGenerator(
    private val personModelsProvider: PersonNicknameModelsProvider
) : NicknameGenerator {
    companion object {
        private const val PEOPLE_PREFIX = "people"

        fun NicknameGenerator.Config.buildQueryKey(): String {
            return "${PEOPLE_PREFIX}_${gender.value}_${alphabet.value}"
        }
    }

    override suspend fun generateNickname(config: NicknameGenerator.Config): Nickname {
        val key = config.buildQueryKey()
        val models = personModelsProvider.getModels()
        return models[key]?.generateNickname(config.nicknameLength).orEmpty()
    }
}