package com.netchar.nicknamer.domen.models

data class Config(
        val nicknameLength: Int = 5,
        val gender: Gender = Gender.MALE,
        val alphabet: Alphabet = Alphabet.LATIN
) {
    enum class Gender(val value: String) { MALE("male"), FEMALE("female") }
    enum class Alphabet(val value: String) { LATIN("latin"), CYRILLIC("cyrillic") }
}