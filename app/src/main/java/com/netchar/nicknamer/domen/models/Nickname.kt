package com.netchar.nicknamer.domen.models


inline class Nickname(val value: String) {
    companion object {
        private val EMPTY = Nickname("")

        fun Nickname?.orEmpty() = this ?: EMPTY
    }
}