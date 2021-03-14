package com.netchar.nicknamer.domen.models

data class Config(val nicknameLength: Int, val gender: Gender, val alphabet: Alphabet) {
    enum class Gender(val value: String) { MALE("male"), FEMALE("female") }
    enum class Alphabet(val value: String) { LATIN("latin"), CYRILLIC("cyrillic") }
}