package com.netchar.nicknamer.domen.service

import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.NicknameModelsDataSource
import com.netchar.nicknamer.domen.models.Nickname.Companion.orEmpty

class NicknameGeneratorServiceImpl(
        private val dataSource: NicknameModelsDataSource
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

    }

    override fun removeFromFavorites(nickname: Nickname) {

    }

    override fun getFavoriteNicknames(): List<Nickname> {
        return listOf()
    }
}