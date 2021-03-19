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