package com.netchar.nicknamer.domen.service

import com.netchar.nicknamer.domen.models.Config
import com.netchar.nicknamer.domen.models.Nickname
import com.netchar.nicknamer.domen.NicknameModelsDataSource

class NicknameGeneratorServiceImpl(
        private val dataSource: NicknameModelsDataSource
) : NicknameGeneratorService {
    companion object {
        private const val PEOPLE_PREFIX = "people"
    }
    override suspend fun generateNickname(config: Config): Nickname {
        val key = "${PEOPLE_PREFIX}_${config.gender.value}_${config.alphabet.value}"
        val source = dataSource.getDataSource()
        return source[key]?.generateNickname(config.nicknameLength) ?: Nickname("")
    }

    override fun addToFavorites(nickname: Nickname) {

    }

    override fun removeFromFavorites(nickname: Nickname) {

    }

    override fun getFavoriteNicknames(): List<Nickname> {
        return listOf()
    }
}