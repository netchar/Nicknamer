package com.netchar.nicknamer.domen.service

import com.netchar.nicknamer.domen.models.Config
import com.netchar.nicknamer.domen.models.Nickname

interface NicknameGeneratorService {
    /**
     * Generates random nickname based on [config]
     */
    suspend fun generateNickname(config: Config) : Nickname

    fun addToFavorites(nickname: Nickname)

    fun removeFromFavorites(nickname: Nickname)

    fun getFavoriteNicknames(): List<Nickname>
}