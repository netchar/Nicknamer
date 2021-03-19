package com.netchar.nicknamer.domen.models


inline class Nickname(val value: String) {
    companion object {
        val EMPTY = Nickname("")

        fun Nickname?.orEmpty() = this ?: EMPTY
    }
}