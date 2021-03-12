package com.netchar.nicknamer.domen

data class Config(val nicknameLength: Int, val gender: Gender) {
    enum class Gender { MALE, FEMALE, INDIFFERENT }
}