package com.netchar.nicknamer.data

import com.netchar.nicknamer.domen.Config
import com.netchar.nicknamer.domen.NicknameGenerator

class NicknameGeneratorImpl : NicknameGenerator {
    companion object {
        private val ALPHABET = 'A'..'Z'
    }

    override suspend fun generate(config: Config): String {
        return (3..config.nicknameLength)
            .map { ALPHABET.random() }
            .joinToString("")
    }
}