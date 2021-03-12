package com.netchar.nicknamer.domen

interface NicknameGenerator {
    /**
     * Generates random nickname based on [config]
     */
    suspend fun generate(config: Config): String
}

