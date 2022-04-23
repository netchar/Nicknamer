package com.netchar.nicknamer.domen

import com.netchar.nicknamer.domen.models.Nickname

interface NicknameGenerator {
    /**
     * Generates random nickname based on [config]
     */
    suspend fun generateNickname(config: Config): Nickname

    data class Config(
        val nicknameLength: Int = 5,
        val gender: Gender = Gender.MALE,
        val alphabet: Alphabet = Alphabet.LATIN
    ) {
        enum class Gender(val value: String) {
            MALE("male"),
            FEMALE("female")
        }

        enum class Alphabet(val value: String) {
            LATIN("latin"),
            CYRILLIC("cyrillic")
        }
    }
}